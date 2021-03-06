package ru.service.test.payment.config

import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapperConfig {

    @Bean
    fun modelMapper(): ModelMapper {
        val modelMapper = ModelMapper()
        modelMapper.configuration
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(false)
                .setFieldMatchingEnabled(true).isAmbiguityIgnored = false
        return modelMapper
    }
}