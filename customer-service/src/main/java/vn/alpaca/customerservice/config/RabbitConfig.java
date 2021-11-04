package vn.alpaca.customerservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.alpaca.common.exception.MessageGlobalExceptionHandler;

import static vn.alpaca.common.constant.MessageExchanges.*;
import static vn.alpaca.common.constant.MessageQueues.*;

@Configuration
public class RabbitConfig {

    @Bean
    Queue activateCustomerDLQueue() {
        return QueueBuilder.durable(ACTIVATE_CUSTOMER_DL_QUEUE.getQueue()).build();
    }

    @Bean
    Queue activateCustomerQueue() {
        return QueueBuilder.durable(ACTIVATE_CUSTOMER_QUEUE.getQueue()).build();
    }

    @Bean
    Queue deactivateCustomerDLQueue() {
        return QueueBuilder.durable(DEACTIVATE_CUSTOMER_DL_QUEUE.getQueue()).build();
    }

    @Bean
    Queue deactivateCustomerQueue() {
        return QueueBuilder.durable(DEACTIVATE_CONTRACT_QUEUE.getQueue()).build();
    }

    @Bean
    TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange(ALPACA_TOPIC_EXCHANGE.getExchange()).build();
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return ExchangeBuilder.fanoutExchange(DEAD_LETTER_EXCHANGE.getExchange()).build();
    }

    @Bean
    Binding activateCustomerDLBinding(Queue activateCustomerQueue, FanoutExchange deadLetterExchange) {
        return BindingBuilder.bind(activateCustomerQueue).to(deadLetterExchange);
    }

    @Bean
    Binding activateCustomerBinding(Queue activateCustomerQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(activateCustomerQueue)
                             .to(topicExchange)
                             .with("customer.activate");
    }

    @Bean
    Binding deactivateCustomerDLBinding(Queue deactivateCustomerQueue, FanoutExchange deadLetterExchange) {
        return BindingBuilder.bind(deactivateCustomerQueue).to(deadLetterExchange);
    }

    @Bean
    Binding deactivateCustomerBinding(Queue deactivateCustomerQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(deactivateCustomerQueue)
                             .to(topicExchange)
                             .with("customer.deactivate");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(new MessageGlobalExceptionHandler());
        return factory;
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory factory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
