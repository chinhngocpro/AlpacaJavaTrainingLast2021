package vn.alpaca.claimrequestservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.claimrequestservice.object.entity.ClaimRequest;
import vn.alpaca.claimrequestservice.object.wrapper.dto.ClaimRequestDTO;
import vn.alpaca.claimrequestservice.object.wrapper.request.ClaimRequestForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClaimRequestMapper {

    ClaimRequestDTO covertToDTO(ClaimRequest request);

    ClaimRequest convertToEntity(ClaimRequestForm claimRequestForm);
}
