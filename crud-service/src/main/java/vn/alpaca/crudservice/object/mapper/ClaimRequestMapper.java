package vn.alpaca.crudservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.crudservice.object.entity.ClaimRequest;
import vn.alpaca.crudservice.object.wrapper.dto.ClaimRequestDTO;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ClaimRequestMapper {

    ClaimRequestDTO covertToDTO(ClaimRequest request);
}
