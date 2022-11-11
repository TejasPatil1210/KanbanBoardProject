package com.kanbanboardproject.kanbanservice.amqpConfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MessageConfiguration {
    private String exchangeName="kanban_exchange";
    private String queueName="kanban_queue";
    private String updateQueue="update_queue";
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    @Qualifier
    @Primary
    public Queue registerQueue()
    {
        return new Queue(queueName);
    }

    @Bean
    public Queue updateQueue()
    {
        return new Queue(updateQueue);
    }

    @Bean
    public Binding userBinding(Queue queue, DirectExchange directExchange)
    {
        return BindingBuilder.bind(queue).to(directExchange).with("user_routing");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(produceConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter produceConverter()
    {
        return new Jackson2JsonMessageConverter();
    }
}
