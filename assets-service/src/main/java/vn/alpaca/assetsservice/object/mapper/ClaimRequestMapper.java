package vn.alpaca.assetsservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.assetsservice.object.entity.ClaimRequest;
import vn.alpaca.assetsservice.object.wrapper.dto.ClaimRequestDTO;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClaimRequestMapper {

    ClaimRequestDTO covertToDTO(ClaimRequest request);
}
