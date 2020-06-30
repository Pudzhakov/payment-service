package ru.service.test.payment.controllers

import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.service.test.payment.dto.AccountMapper
import ru.service.test.payment.dto.request.AccountOperationRequestDto
import ru.service.test.payment.dto.request.TransferMoneyBetweenAccountsRequestDto
import ru.service.test.payment.dto.response.AccountResponseDto
import ru.service.test.payment.services.AccountService
import javax.validation.Valid

@Api(tags = ["Accounts API"])
@RestController
@RequestMapping("/api/payment/v1/accounts")
class AccountController(
        val accountService: AccountService,
        val accountMapper: AccountMapper
) {

    @GetMapping("/{id}")
    fun getAccountBalance(@PathVariable id: Long): ResponseEntity<AccountResponseDto> {
        val account = accountService.getAccountById(id)
        return ResponseEntity.ok(accountMapper.toDto(account))
    }

    @PutMapping("/transfer")
    fun transferMoneyToAnotherAccount(@Valid @RequestBody model: TransferMoneyBetweenAccountsRequestDto): ResponseEntity<Any> {
        accountService.transferMoney(model)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/withdraw")
    fun withdrawMoneyFromAccount(@Valid @RequestBody model: AccountOperationRequestDto): ResponseEntity<Any> {
        accountService.withdrawMoney(model)
        return ResponseEntity.ok().build()
    }

    @PutMapping("/deposit")
    fun depositMoneyToAccount(@Valid @RequestBody model: AccountOperationRequestDto): ResponseEntity<Any> {
        accountService.depositMoney(model)
        return ResponseEntity.ok().build()
    }

}