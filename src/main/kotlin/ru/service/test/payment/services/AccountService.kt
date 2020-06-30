package ru.service.test.payment.services

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.service.test.payment.common.exceptions.TransactionException
import ru.service.test.payment.dto.request.AccountOperationRequestDto
import ru.service.test.payment.dto.request.TransferMoneyBetweenAccountsRequestDto
import ru.service.test.payment.entities.AccountEntity
import ru.service.test.payment.repositories.AccountRepository
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class AccountService(
        var accountRepository: AccountRepository
) {

    companion object {
        private val logger = LoggerFactory.getLogger(AccountService::class.java)
    }

    @Throws(TransactionException::class)
    fun getAccountById(accountId: Long): AccountEntity = accountRepository.findById(accountId).orElseThrow { TransactionException("Account not found with id: $accountId") }

    @Synchronized
    @Throws(TransactionException::class)
    @Transactional
    fun transferMoney(transferMoney: TransferMoneyBetweenAccountsRequestDto) {

        val fromAccount = getAccountById(transferMoney.fromAccountId)
        val toAccount = getAccountById(transferMoney.toAccountId)

        decreaseMoney(fromAccount, transferMoney.amount)
        increaseMoney(toAccount, transferMoney.amount)

        logger.info("Successful transfer")

    }

    @Throws(TransactionException::class)
    @Transactional
    fun withdrawMoney(model: AccountOperationRequestDto) {

        val account = getAccountById(model.accountId)
        decreaseMoney(account, model.amount)

    }

    @Throws(TransactionException::class)
    @Transactional
    fun depositMoney(model: AccountOperationRequestDto) {

        val account = getAccountById(model.accountId)
        increaseMoney(account, model.amount)

    }

    @Throws(TransactionException::class)
    fun increaseMoney(account: AccountEntity, money: BigDecimal) {
        account.balance += money
    }

    @Throws(TransactionException::class)
    fun decreaseMoney(account: AccountEntity, money: BigDecimal) {
        checkAccountBalanceDecreasing(account, money)
        account.balance -= money
    }

    fun checkAccountBalanceDecreasing(account: AccountEntity, money: BigDecimal) {
        if((account.balance - money) < BigDecimal.ZERO )
            throw TransactionException("The money in the account ${account.id} is not enough, balance: ${account.balance}")
    }

}