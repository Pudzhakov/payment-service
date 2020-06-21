package ru.service.test.payment.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import ru.service.test.payment.common.exceptions.TransactionException
import ru.service.test.payment.entities.AccountEntity
import ru.service.test.payment.models.request.AccountOperationRequestModel
import ru.service.test.payment.models.request.TransferMoneyBetweenAccountsRequestModel
import ru.service.test.payment.repositories.AccountRepository
import java.math.BigDecimal

@Service
class AccountService() {

    private final lateinit var accountRepository: AccountRepository
    
    @Autowired
    constructor(accountRepository: AccountRepository): this() {
        this.accountRepository = accountRepository
    }

    @Throws(TransactionException::class)
    fun getAccountById(accountId: Long): AccountEntity = accountRepository.findById(accountId).orElseThrow { TransactionException("Account not found with id: $accountId") }

    fun getAccountBalance(accountId: Long) = getAccountById(accountId).balance

    @Throws(TransactionException::class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun transferMoney(transferMoney: TransferMoneyBetweenAccountsRequestModel) {

        val fromAccount = getAccountById(transferMoney.fromAccountId)
        val toAccount = getAccountById(transferMoney.toAccountId)

        decreaseMoney(fromAccount, transferMoney.amount)
        increaseMoney(toAccount, transferMoney.amount)

    }

    @Throws(TransactionException::class)
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [TransactionException::class])
    fun withdrawMoney(model: AccountOperationRequestModel) {
        val account = getAccountById(model.accountId)
        decreaseMoney(account, model.amount)
    }

    @Throws(TransactionException::class)
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = [TransactionException::class])
    fun putMoney(model: AccountOperationRequestModel) {
        val account = getAccountById(model.accountId)
        increaseMoney(account, model.amount)
    }

    @Throws(TransactionException::class)
    @Transactional(propagation = Propagation.MANDATORY)
    fun increaseMoney(account: AccountEntity, money: BigDecimal) {
        account.balance += money
    }

    @Throws(TransactionException::class)
    @Transactional(propagation = Propagation.MANDATORY)
    fun decreaseMoney(account: AccountEntity, money: BigDecimal) {
        checkAccountBalanceDecreasing(account, money)
        account.balance -= money
    }

    fun checkAccountBalanceDecreasing(account: AccountEntity, money: BigDecimal) {
        if((account.balance - money) < BigDecimal.ZERO )
            throw TransactionException("The money in the account ${account.id} is not enough, balance: ${account.balance}")
    }

}