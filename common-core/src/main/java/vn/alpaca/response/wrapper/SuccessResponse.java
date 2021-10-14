package vn.alpaca.response.wrapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuccessResponse<S> extends AbstractResponse {

    @JsonProperty("data")
    S data;

    public SuccessResponse(S data) {
        super(200);
        this.data = data;
    }
}