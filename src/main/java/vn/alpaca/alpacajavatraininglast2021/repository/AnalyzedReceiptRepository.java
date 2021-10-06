package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;

import java.util.Collection;
import java.util.Optional;

public interface AnalyzedReceiptRepository extends
        JpaRepository<AnalyzedReceipt, Integer>,
        JpaSpecificationExecutor<AnalyzedReceipt> {
}
