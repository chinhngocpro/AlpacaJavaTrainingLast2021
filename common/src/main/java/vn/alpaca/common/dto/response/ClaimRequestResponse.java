package vn.alpaca.common.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ClaimRequestResponse {

    private String title;

    private String description;

    private List<String> receiptPhotos;

    private String status;
}
