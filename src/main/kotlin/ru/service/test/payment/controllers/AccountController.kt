package ru.service.test.payment.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.service.test.payment.models.request.AccountOperationRequestModel
import ru.service.test.payment.models.request.TransferMoneyBetweenAccountsRequestModel
import ru.service.test.payment.models.response.AccountResponseModel
import ru.service.test.payment.services.AccountService

@RestController
@RequestMapping("/api/payment/v1/accounts")
class AccountController() {

    private final lateinit var accountService: AccountService

    @Autowired
    constructor(accountService: AccountService): this() {
        this.accountService = accountService
    }

    @GetMapping("/{id}")
    fun getAccountBalance(@PathVariable id: Long): ResponseEntity<Any> {
        val balance = accountService.getAccountBalance(id)
        return ResponseEntity.ok(AccountResponseModel(balance))
    }

    @PatchMapping("/money/transfer")
    fun transferMoneyToAnotherAccount(@RequestBody model: TransferMoneyBetweenAccountsRequestModel): ResponseEntity<Any> {

        accountService.transferMoney(model)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/money/withdraw")
    fun withdrawMoneyFromAccount(@RequestBody model: AccountOperationRequestModel): ResponseEntity<Any> {
        accountService.withdrawMoney(model)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/money/put")
    fun putMoneyToAccount(@RequestBody model: AccountOperationRequestModel): ResponseEntity<Any> {
        accountService.putMoney(model)
        return ResponseEntity.ok().build()
    }

}