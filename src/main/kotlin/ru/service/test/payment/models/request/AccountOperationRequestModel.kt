package ru.service.test.payment.models.request

import com.sun.istack.NotNull
import java.math.BigDecimal
import javax.validation.constraints.Min

class AccountOperationRequestModel(@NotNull var accountId: Long,
                                   @NotNull @Min(0) var amount: BigDecimal)