package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;

import java.util.Collection;
import java.util.Optional;

public interface ReceiptRepository
        extends JpaRepository<AnalyzedReceipt, Integer> {

    @Query("SELECT o FROM AnalyzedReceipt o WHERE o.isValid = true")
    Collection<AnalyzedReceipt> findAllByValidIsTrue();

    @Query("SELECT o FROM AnalyzedReceipt o WHERE o.isValid = true")
    Page<AnalyzedReceipt> findAllByValidIsTrue(Pageable pageable);

    Collection<AnalyzedReceipt> findAllByAnalyzerId(int userId);

    Page<AnalyzedReceipt> findAllByAnalyzerId(int userId, Pageable pageable);

    Optional<AnalyzedReceipt> findByClaimRequestId(int requestId);
}
