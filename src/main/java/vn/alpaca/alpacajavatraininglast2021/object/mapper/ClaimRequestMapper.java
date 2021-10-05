package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ClaimRequestDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;

@Mapper(componentModel = "spring")
public interface ClaimRequestMapper {

    ClaimRequestDTO covertToDTO(ClaimRequest request);
}
