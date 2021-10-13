package vn.alpaca.crudservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.crudservice.object.entity.AnalyzedReceipt;
import vn.alpaca.crudservice.object.wrapper.dto.AnalyzedReceiptDTO;
import vn.alpaca.crudservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface AnalyzerReceiptMapper {

    AnalyzedReceiptDTO convertToDTO(AnalyzedReceipt receipt);

    AnalyzedReceipt convertToEntity(AnalyzedReceiptForm analyzedReceiptForm);
}
