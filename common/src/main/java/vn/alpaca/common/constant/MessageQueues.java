package vn.alpaca.common.constant;

public enum MessageQueues {

    ACTIVATE_CUSTOMER_QUEUE("activate_customer_queue"),
    ACTIVATE_CUSTOMER_DL_QUEUE("activate_customer_dl_queue"),
    DEACTIVATE_CUSTOMER_QUEUE("deactivate_customer_queue"),
    DEACTIVATE_CUSTOMER_DL_QUEUE("deactivate_customer_dl_queue"),
    ACTIVATE_CONTRACT_QUEUE("activate_contract_queue"),
    ACTIVATE_CONTRACT_DL_QUEUE("activate_contract_dl_queue"),
    DEACTIVATE_CONTRACT_QUEUE("deactivate_contract_queue"),
    DEACTIVATE_CONTRACT_DL_QUEUE("deactivate_contract_dl_queue");

    private final String queueName;

    MessageQueues(String queueName) {
        this.queueName = queueName;
    }

    public String getQueue() {
        return queueName;
    }
}
