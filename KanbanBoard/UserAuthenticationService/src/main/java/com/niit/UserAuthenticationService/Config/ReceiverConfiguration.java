package com.niit.UserAuthenticationService.Config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceiverConfiguration {
    @Bean
    public Jackson2JsonMessageConverter producerConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
