package vn.alpaca.sendclaimrequestservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.sendclaimrequestservice.object.entity.ClaimRequest;
import vn.alpaca.sendclaimrequestservice.object.wrapper.response.ClaimRequestDTO;
import vn.alpaca.sendclaimrequestservice.object.wrapper.request.ClaimRequestForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClaimRequestMapper {

    ClaimRequestDTO covertToDTO(ClaimRequest request);

    ClaimRequest convertToEntity(ClaimRequestForm claimRequestForm);
}
