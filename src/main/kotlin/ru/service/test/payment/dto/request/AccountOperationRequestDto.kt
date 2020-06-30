package ru.service.test.payment.dto.request

import com.sun.istack.NotNull
import java.math.BigDecimal
import javax.validation.constraints.Min

data class AccountOperationRequestDto(@NotNull var accountId: Long,
                                      @NotNull @Min(0) var amount: BigDecimal)