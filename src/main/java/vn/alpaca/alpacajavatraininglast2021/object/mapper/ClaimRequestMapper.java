package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ClaimRequestDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.object.request.claimrequest.ClaimRequestForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClaimRequestMapper {

    ClaimRequestDTO covertToDTO(ClaimRequest request);

    ClaimRequest convertToEntity(ClaimRequestForm claimRequestForm);
}
