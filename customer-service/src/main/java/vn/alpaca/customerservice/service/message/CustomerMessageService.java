package vn.alpaca.customerservice.service.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.response.CustomerResponse;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomerMessageService {

    private final AmqpTemplate template;
    protected final String DEFAULT_EXCHANGE = "alpaca.topic";

    public void onUpdateCustomerStatus(String routingKey, CustomerResponse response) {
        template.convertAndSend(DEFAULT_EXCHANGE, routingKey, response);
        log.info("PUBLISHED MESSAGE: " + response);
    }
}
