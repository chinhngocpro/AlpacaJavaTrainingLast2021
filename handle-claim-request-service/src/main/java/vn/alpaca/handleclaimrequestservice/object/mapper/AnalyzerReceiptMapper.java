package vn.alpaca.handleclaimrequestservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.handleclaimrequestservice.object.entity.AnalyzedReceipt;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.AnalyzedReceiptResponse;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptRequest;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface AnalyzerReceiptMapper {

    AnalyzedReceiptResponse convertToResponseModel(AnalyzedReceipt receipt);

    AnalyzedReceipt convertToEntity(AnalyzedReceiptRequest requestData);
}
