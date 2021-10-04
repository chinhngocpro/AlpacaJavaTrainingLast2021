package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exceptions.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.exceptions.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.repositories.ReceiptRepository;

import java.util.Collection;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public Collection<AnalyzedReceipt> findAllReceipts() {
        return receiptRepository.findAll();
    }

    @Override
    public Page<AnalyzedReceipt> findAllReceipts(Pageable pageable) {
        return receiptRepository.findAll(pageable);
    }

    @Override
    public Collection<AnalyzedReceipt> findValidReceipts() {
        return receiptRepository.findAllByValidIsTrue();
    }

    @Override
    public Page<AnalyzedReceipt> findValidReceipts(Pageable pageable) {
        return receiptRepository.findAllByValidIsTrue(pageable);
    }

    @Override
    public Collection<AnalyzedReceipt> findReceiptsByUserId(int userId) {
        return receiptRepository.findAllByAnalyzerId(userId);
    }

    @Override
    public Page<AnalyzedReceipt>
    findReceiptsByUserId(int userId, Pageable pageable) {
        return receiptRepository.findAllByAnalyzerId(userId, pageable);
    }

    @Override
    public AnalyzedReceipt findReceiptById(int id) {
        return receiptRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public AnalyzedReceipt findReceiptByRequestId(int requestId) {
        return receiptRepository.findByRequestId(requestId)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public AnalyzedReceipt saveReceipt(AnalyzedReceipt receipt) {
        return receiptRepository.save(receipt);
    }

    @Override
    public AnalyzedReceipt saveInChargeReceipt(int userId,
                                               AnalyzedReceipt receipt) {
        if (receipt.getAnalyzer().getId() != userId) {
            throw new AccessDeniedException(); // TODO: implement exception message
        }

        return receiptRepository.save(receipt);
    }

    @Override
    public void validateReceipt(int receiptId) {
        AnalyzedReceipt receipt = findReceiptById(receiptId);
        receipt.setValid(true);
        saveReceipt(receipt);
    }

    @Override
    public void invalidateReceipt(int receiptId) {
        AnalyzedReceipt receipt = findReceiptById(receiptId);
        receipt.setValid(false);
        saveReceipt(receipt);
    }
}
