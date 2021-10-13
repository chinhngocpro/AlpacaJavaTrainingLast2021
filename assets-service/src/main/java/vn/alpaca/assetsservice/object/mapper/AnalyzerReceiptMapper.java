package vn.alpaca.assetsservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.assetsservice.object.entity.AnalyzedReceipt;
import vn.alpaca.assetsservice.object.wrapper.dto.AnalyzedReceiptDTO;
import vn.alpaca.assetsservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface AnalyzerReceiptMapper {

    AnalyzedReceiptDTO convertToDTO(AnalyzedReceipt receipt);

    AnalyzedReceipt convertToEntity(AnalyzedReceiptForm analyzedReceiptForm);
}
