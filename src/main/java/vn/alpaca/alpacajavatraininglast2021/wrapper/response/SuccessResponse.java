package vn.alpaca.alpacajavatraininglast2021.wrapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessResponse<S> extends AbstractResponse {

    @JsonProperty("data")
    S data;

    public SuccessResponse(S data) {
        super(0);

        this.data = data;
    }

    public S getData() {
        return data;
    }

    public void setData(S data) {
        this.data = data;
    }
}
