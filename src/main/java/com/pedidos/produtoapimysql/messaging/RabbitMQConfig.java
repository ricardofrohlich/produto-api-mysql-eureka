package com.pedidos.produtoapimysql.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "pedidos.exchange";

    public static final String QUEUE_BAIXAR_ESTOQUE =
            "produto.baixar-estoque.queue";

    public static final String ROUTING_KEY_BAIXAR_ESTOQUE =
            "estoque.baixar";

    public static final String ROUTING_KEY_CONFIRMADO =
            "estoque.confirmado";

    public static final String ROUTING_KEY_RECUSADO =
            "estoque.recusado";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue queueBaixarEstoque() {
        return new Queue(QUEUE_BAIXAR_ESTOQUE);
    }

    @Bean
    public Binding bindingBaixarEstoque() {
        return BindingBuilder
                .bind(queueBaixarEstoque())
                .to(exchange())
                .with(ROUTING_KEY_BAIXAR_ESTOQUE);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
