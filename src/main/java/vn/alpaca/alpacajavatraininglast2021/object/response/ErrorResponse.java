package vn.alpaca.alpacajavatraininglast2021.object.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ErrorResponse extends AbstractResponse {

    @JsonProperty("errors")
    Map<String, String> errors;

    public ErrorResponse(int errorCode, String message) {
        setResponseCode(errorCode);
        setMessage(message);
    }

    public ErrorResponse(
            int responseCode,
            String message,
            Map<String, String> errors
    ) {
        setResponseCode(responseCode);
        setMessage(message);
        this.errors = errors;
    }
}
