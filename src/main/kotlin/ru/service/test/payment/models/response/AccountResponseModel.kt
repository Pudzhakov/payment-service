package ru.service.test.payment.models.response

import java.math.BigDecimal
import javax.validation.constraints.NotNull

class AccountResponseModel(@NotNull var balance: BigDecimal)