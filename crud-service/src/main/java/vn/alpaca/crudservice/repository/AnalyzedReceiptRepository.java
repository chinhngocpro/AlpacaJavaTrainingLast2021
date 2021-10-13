package vn.alpaca.crudservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.crudservice.object.entity.AnalyzedReceipt;

public interface AnalyzedReceiptRepository extends
        JpaRepository<AnalyzedReceipt, Integer>,
        JpaSpecificationExecutor<AnalyzedReceipt> {
}

