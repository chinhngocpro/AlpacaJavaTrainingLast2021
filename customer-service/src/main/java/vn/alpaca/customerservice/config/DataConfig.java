package vn.alpaca.customerservice.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import vn.alpaca.common.config.SpringDataConfig;

@Configuration
public class DataConfig extends SpringDataConfig {

    @Override
    public RestHighLevelClient restClient() {
        return super.restClient();
    }

    @Override
    public ElasticsearchOperations esTemplate() {
        return super.esTemplate();
    }

    @Override
    public RedissonClient redissonClient() {
        return super.redissonClient();
    }

    @Override
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient client) {
        return super.redissonConnectionFactory(client);
    }
}
