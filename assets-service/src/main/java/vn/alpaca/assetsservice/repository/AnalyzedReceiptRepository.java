package vn.alpaca.assetsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.assetsservice.object.entity.AnalyzedReceipt;

public interface AnalyzedReceiptRepository extends
        JpaRepository<AnalyzedReceipt, Integer>,
        JpaSpecificationExecutor<AnalyzedReceipt> {
}

