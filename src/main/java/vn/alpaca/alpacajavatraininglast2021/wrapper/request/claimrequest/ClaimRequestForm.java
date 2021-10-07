package vn.alpaca.alpacajavatraininglast2021.wrapper.request.claimrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.alpaca.alpacajavatraininglast2021.util.validation.NotEmptyFiles;
import vn.alpaca.alpacajavatraininglast2021.util.validation.ValidImages;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequestForm {

    private Integer customerId;

    private String title;

    private String description;

    @NotEmptyFiles
    @ValidImages
    private List<MultipartFile> receiptPhotoFiles;

}
