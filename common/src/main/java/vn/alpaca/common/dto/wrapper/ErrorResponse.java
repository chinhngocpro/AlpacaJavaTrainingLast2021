package vn.alpaca.common.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"status_code", "message", "timestamp", "errors"})
public final class ErrorResponse extends AbstractResponse {

    private Map<String, String> errors;

    public ErrorResponse(int errorCode, String message) {
        setStatusCode(errorCode);
        setMessage(message);
    }
}
