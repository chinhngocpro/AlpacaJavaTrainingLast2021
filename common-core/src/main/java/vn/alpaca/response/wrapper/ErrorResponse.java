package vn.alpaca.response.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorResponse extends AbstractResponse {

    @JsonProperty("errors")
    Map<String, String> errors;

    public ErrorResponse(int errorCode, String message) {
        super(errorCode, message);
    }

    public ErrorResponse(
            int responseCode,
            String message,
            Map<String, String> errors
    ) {
        super(responseCode, message);
        this.errors = errors;
    }
}