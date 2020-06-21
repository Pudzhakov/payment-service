package ru.service.test.payment.models.request

import com.sun.istack.NotNull
import java.math.BigDecimal
import javax.validation.constraints.Min

class TransferMoneyBetweenAccountsRequestModel(@NotNull var fromAccountId: Long,
                                               @NotNull var toAccountId: Long,
                                               @NotNull @Min(0) var amount: BigDecimal)