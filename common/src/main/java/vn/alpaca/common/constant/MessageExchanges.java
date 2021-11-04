package vn.alpaca.common.constant;

public enum MessageExchanges {

    DEAD_LETTER_EXCHANGE("alpaca.dlx"),
    ALPACA_DIRECT_EXCHANGE("alpaca.direct"),
    ALPACA_TOPIC_EXCHANGE("alpaca.topic"),
    ALPACA_FANOUT_EXCHANGE("alpaca.fanout");

    private final String exchangeName;

    MessageExchanges(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchange() {
        return exchangeName;
    }
}
