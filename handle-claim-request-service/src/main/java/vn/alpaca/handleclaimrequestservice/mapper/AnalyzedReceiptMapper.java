package vn.alpaca.handleclaimrequestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.dto.request.AnalyzedReceiptRequest;
import vn.alpaca.common.dto.response.AnalyzedReceiptResponse;
import vn.alpaca.handleclaimrequestservice.entity.AnalyzedReceipt;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AnalyzedReceiptMapper {

  AnalyzedReceiptResponse convertToResponseModel(AnalyzedReceipt receipt);

  AnalyzedReceipt createReceipt(AnalyzedReceiptRequest requestData);

  void updateReceipt(@MappingTarget AnalyzedReceipt receipt, AnalyzedReceiptRequest requestData);
}
