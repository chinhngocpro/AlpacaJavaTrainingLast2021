package vn.alpaca.alpacajavatraininglast2021.wrapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageResponse<S> extends SuccessResponse<S> {

    @JsonProperty("current")
    long currentPage;

    @JsonProperty("total")
    long totalPage;

    public PageResponse(S data) {
        super(data);
    }

    public PageResponse(S data, long currentPage, long totalPage) {
        super(data);
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
}
