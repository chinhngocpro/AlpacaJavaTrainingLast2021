package vn.alpaca.common.dto.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public abstract class AbstractResponse {

    @JsonProperty("status_code")
    private int statusCode;

    private String message;

    private final Date timestamp = new Date();

    public AbstractResponse(int statusCode) {
        this.statusCode = statusCode;
    }
}
