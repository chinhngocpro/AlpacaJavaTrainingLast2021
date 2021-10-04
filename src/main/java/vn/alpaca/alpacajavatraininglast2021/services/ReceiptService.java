package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.AnalyzedReceipt;

import java.util.Collection;

public interface ReceiptService {

    // 1. Read all receipts
    Collection<AnalyzedReceipt> findAllReceipts();

    Page<AnalyzedReceipt> findAllReceipts(Pageable pageable);

    // 2. Read specific receipts (apply searching)
    Collection<AnalyzedReceipt> findValidReceipts();

    Page<AnalyzedReceipt> findValidReceipts(Pageable pageable);

    Collection<AnalyzedReceipt> findReceiptsByUserId(int userId);

    Page<AnalyzedReceipt> findReceiptsByUserId(int userId, Pageable pageable);

    AnalyzedReceipt findReceiptById(int id);

    AnalyzedReceipt findReceiptByRequestId(int requestId);

    // 3. Create new receipt / Edit available receipts
    AnalyzedReceipt saveReceipt(AnalyzedReceipt receipt);

    // 4. Edit receipts which made by him/her (for analyzer)
    AnalyzedReceipt saveInChargeReceipt(int userId, AnalyzedReceipt receipt);

    // 5. Set validate for receipt
    void validateReceipt(int receiptId);

    void invalidateReceipt(int receiptId);
}
