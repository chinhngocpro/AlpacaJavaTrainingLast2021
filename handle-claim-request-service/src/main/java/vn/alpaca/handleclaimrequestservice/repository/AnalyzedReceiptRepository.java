package vn.alpaca.handleclaimrequestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.handleclaimrequestservice.entity.AnalyzedReceipt;

public interface AnalyzedReceiptRepository
    extends JpaRepository<AnalyzedReceipt, Integer>, JpaSpecificationExecutor<AnalyzedReceipt> {}
