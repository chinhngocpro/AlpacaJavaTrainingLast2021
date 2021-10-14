package vn.alpaca.response.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractResponse {

    @JsonProperty("status")
    int responseCode;

    @JsonProperty("message")
    String message;

    @JsonProperty("timestamp")
    Date timeStamp = new Date();

    public AbstractResponse(int responseCode) {
        this.responseCode = responseCode;
    }

    public AbstractResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }
}