package vn.alpaca.alpacajavatraininglast2021.wrapper.request.claimrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.alpaca.alpacajavatraininglast2021.object.dto.CustomerDTO;
import vn.alpaca.alpacajavatraininglast2021.util.validation.NotEmptyFile;
import vn.alpaca.alpacajavatraininglast2021.util.validation.ValidEmail;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRequestForm {

    private Integer customerId;

    private String title;

    private String description;

    private List<MultipartFile> receiptPhotoFiles;

}
