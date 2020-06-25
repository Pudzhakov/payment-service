package ru.service.test.payment.services

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

    @Throws(TransactionException::class)
    fun getAccountById(accountId: Long): AccountEntity = accountRepository.findById(accountId).orElseThrow { TransactionException("Account not found with id: $accountId") }

    fun getAccountBalance(accountId: Long) = getAccountById(accountId).balance

    @Throws(TransactionException::class)
    fun transferMoney(transferMoney: TransferMoneyBetweenAccountsRequestDto) {

        val fromAccount = getAccountById(transferMoney.fromAccountId)
        val toAccount = getAccountById(transferMoney.toAccountId)

        decreaseMoney(fromAccount, transferMoney.amount)
        increaseMoney(toAccount, transferMoney.amount)

    }

    @Throws(TransactionException::class)
    fun withdrawMoney(model: AccountOperationRequestDto) {
        val account = getAccountById(model.accountId)
        decreaseMoney(account, model.amount)
    }

    @Throws(TransactionException::class)
    fun putMoney(model: AccountOperationRequestDto) {
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