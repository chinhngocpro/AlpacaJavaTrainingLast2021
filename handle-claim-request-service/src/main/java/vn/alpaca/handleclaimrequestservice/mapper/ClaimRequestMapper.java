package vn.alpaca.handleclaimrequestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.dto.response.ClaimRequestResponse;
import vn.alpaca.handleclaimrequestservice.entity.ClaimRequest;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClaimRequestMapper {

    ClaimRequestResponse convertToResponseModel(ClaimRequest request);
}
