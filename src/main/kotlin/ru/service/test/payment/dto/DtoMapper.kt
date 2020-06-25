package ru.service.test.payment.dto

interface DtoMapper<RequestDto, ResponseDto, Model> {
    fun toDto(model: Model): ResponseDto
    fun toModel(dto: RequestDto): Model
}