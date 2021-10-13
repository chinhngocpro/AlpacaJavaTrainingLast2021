package vn.alpaca.crudservice.object.wrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequestDTO {

    private int id;

    private String title;

    private String description;

    private List<String> receiptPhotos;

    private String status;

}
