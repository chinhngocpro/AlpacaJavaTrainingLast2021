package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.alpacajavatraininglast2021.object.dto.AnalyzedReceiptDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.object.request.analyzedreceipt.AnalyzedReceiptForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface AnalyzerReceiptMapper {

    AnalyzedReceiptDTO convertToDTO(AnalyzedReceipt receipt);

    AnalyzedReceipt convertToEntity(AnalyzedReceiptForm analyzedReceiptForm);
}
