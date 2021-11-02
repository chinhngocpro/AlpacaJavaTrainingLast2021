package vn.alpaca.sendclaimrequestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.dto.request.ClaimRequestForm;
import vn.alpaca.common.dto.response.ClaimRequestResponse;
import vn.alpaca.sendclaimrequestservice.entity.ClaimRequest;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClaimRequestMapper {

    ClaimRequestResponse convertToResponseModel(ClaimRequest request);

    ClaimRequest createClaimRequest(ClaimRequestForm claimRequestForm);
}
