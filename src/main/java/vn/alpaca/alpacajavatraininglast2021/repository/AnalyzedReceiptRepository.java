package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;

public interface AnalyzedReceiptRepository extends
        JpaRepository<AnalyzedReceipt, Integer>,
        JpaSpecificationExecutor<AnalyzedReceipt> {
}
