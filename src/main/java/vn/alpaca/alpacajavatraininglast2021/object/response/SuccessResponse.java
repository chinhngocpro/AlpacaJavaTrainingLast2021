package vn.alpaca.alpacajavatraininglast2021.object.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SuccessResponse<S> extends AbstractResponse {

    @JsonProperty("data")
    S data;

    public SuccessResponse(S data) {
        super(200);
        this.data = data;
    }
}
