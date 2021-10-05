package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import vn.alpaca.alpacajavatraininglast2021.object.dto.AnalyzedReceiptDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;

@Mapper(componentModel = "spring")
public interface AnalyzerReceiptMapper {

    AnalyzedReceiptDTO convertToDTO(AnalyzedReceipt receipt);
}
