package ru.service.test.payment

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.service.test.payment.dto.request.AccountOperationRequestDto
import ru.service.test.payment.dto.request.TransferMoneyBetweenAccountsRequestDto
import ru.service.test.payment.services.AccountService
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class PaymentServiceApplicationTests {

	@Autowired
	lateinit var mockMvc: MockMvc

	@Autowired
	lateinit var objectMapper: ObjectMapper

	@Autowired
	lateinit var accountService: AccountService

	@Test
	fun getAccount() {

		mockMvc.perform(get("/api/payment/v1/accounts/{id}", 1))
				.andExpect(status().isOk)

	}

	@Test
	fun transfer() {

		val dto = TransferMoneyBetweenAccountsRequestDto(1,2, BigDecimal(10.00))

		var firstAccount = accountService.getAccountById(1)
		var secondAccount = accountService.getAccountById(2)

		val firstAccountBalance = firstAccount.balance
		val secondAccountBalance = secondAccount.balance

		mockMvc.perform(put("/api/payment/v1/accounts/transfer")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk)

		firstAccount = accountService.getAccountById(1)
		secondAccount = accountService.getAccountById(2)

		assertThat(firstAccount.balance).isEqualTo(firstAccountBalance - BigDecimal(10.00))
		assertThat(secondAccount.balance).isEqualTo(secondAccountBalance + BigDecimal(10.00))

	}

	@Test
	fun deposit() {

		val dto = AccountOperationRequestDto(1, BigDecimal(10.00))

		var account = accountService.getAccountById(1)

		val accountBalance = account.balance

		mockMvc.perform(put("/api/payment/v1/accounts/deposit")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk)

		account = accountService.getAccountById(1)

		assertThat(account.balance).isEqualTo(accountBalance + BigDecimal(10.00))

	}

	@Test
	fun withdraw() {

		val dto = AccountOperationRequestDto(1, BigDecimal(10.00))

		var account = accountService.getAccountById(1)

		val accountBalance = account.balance

		mockMvc.perform(put("/api/payment/v1/accounts/withdraw")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk)

		account = accountService.getAccountById(1)

		assertThat(account.balance).isEqualTo(accountBalance - BigDecimal(10.00))

	}

}
