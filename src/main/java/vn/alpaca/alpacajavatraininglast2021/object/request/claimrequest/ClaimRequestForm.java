package vn.alpaca.alpacajavatraininglast2021.object.request.claimrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.alpaca.alpacajavatraininglast2021.util.validation.NotEmptyFiles;
import vn.alpaca.alpacajavatraininglast2021.util.validation.ValidImages;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequestForm {

    @NotBlank(message = "blank")
    private String title;

    @NotBlank(message = "blank")
    private String description;

    @NotEmptyFiles(message = "empty")
    @ValidImages(message = "wrong-format")
    private List<MultipartFile> receiptPhotoFiles;

}
