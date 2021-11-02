package vn.alpaca.common.dto.request;

import lombok.Data;

@Data
public class ClaimRequestFilter {

    private String title;
    private String description;
    private String status;

    private Pagination pagination = new Pagination(5);
}
