package vn.alpaca.alpacajavatraininglast2021.wrapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractResponse {

    @JsonProperty("msg")
    String message;

    @JsonProperty("errorCode")
    int errorCode;

    public AbstractResponse(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
