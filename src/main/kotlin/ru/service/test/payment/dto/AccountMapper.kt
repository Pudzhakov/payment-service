package ru.service.test.payment.dto

import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import ru.service.test.payment.dto.request.AccountOperationRequestDto
import ru.service.test.payment.dto.response.AccountResponseDto
import ru.service.test.payment.entities.AccountEntity

@Service
class AccountMapper (val mapper: ModelMapper): DtoMapper<AccountOperationRequestDto, AccountResponseDto, AccountEntity> {

    override fun toDto(model: AccountEntity): AccountResponseDto {
        return mapper.map(model, AccountResponseDto::class.java)
    }

    override fun toModel(dto: AccountOperationRequestDto): AccountEntity {
        return mapper.map(dto, AccountEntity::class.java)
    }

}