package ru.service.test.payment.repositories

import org.springframework.data.repository.CrudRepository
import ru.service.test.payment.entities.AccountEntity

interface AccountRepository: CrudRepository<AccountEntity, Long>