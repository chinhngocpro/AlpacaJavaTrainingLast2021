package vn.alpaca.common.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ClaimRequestForm {

    @NotBlank(message = "blank")
    private String idCardNumber;

    @NotBlank(message = "blank")
    private String title;

    @NotBlank(message = "blank")
    private String description;

    @NotNull(message = "null")
    private List<MultipartFile> receiptPhotoFiles;
}
