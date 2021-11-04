package vn.alpaca.userservice.service.message;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import vn.alpaca.userservice.entity.jpa.User;

@Service
@RequiredArgsConstructor
public class UserMessageService {

    private final AmqpTemplate template;
    private final String DEFAULT_EXCHANGE = "alpaca.topic";

    public void send(String routingKey, User data) {
        template.convertAndSend(DEFAULT_EXCHANGE, routingKey, data);
    }

    public void send(String exchangeName, String routingKey, User data) {
        template.convertAndSend(exchangeName, routingKey, data);
    }
}
