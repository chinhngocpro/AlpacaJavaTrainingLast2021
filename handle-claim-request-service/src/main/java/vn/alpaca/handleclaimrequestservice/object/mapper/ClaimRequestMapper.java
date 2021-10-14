package vn.alpaca.handleclaimrequestservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.handleclaimrequestservice.object.entity.ClaimRequest;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.ClaimRequestResponse;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClaimRequestMapper {

    ClaimRequestResponse convertToResponseModel(ClaimRequest request);
}
