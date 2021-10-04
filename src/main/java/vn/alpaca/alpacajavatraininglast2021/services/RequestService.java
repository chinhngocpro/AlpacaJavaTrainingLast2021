package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Request;

import java.util.Collection;

public interface RequestService {

    // 1. View all available request
    Collection<Request> findAllRequests();

    Page<Request> findAllRequests(Pageable pageable);

    // 2. View specific requests (apply searching)
    Collection<Request> findRequestsByNameContains(String name);

    Page<Request> findRequestsByNameContains(String name, Pageable pageable);

    Collection<Request> findRequestsByDescriptionContains(String description);

    Page<Request>
    findRequestsByDescriptionContains(String description, Pageable pageable);

    Collection<Request> findRequestsByCustomerId(int customerId);

    Page<Request> findRequestsByCustomerId(int customerId, Pageable pageable);

    Collection<Request> findRequestsByUserId(int userId);

    Page<Request> findRequestsByUserId(int userId, Pageable pageable);

    Collection<Request> findRequestsByStatus(String status);

    Page<Request> findRequestsByStatus(String status, Pageable pageable);

    Request findRequestById(int id);

    // 3. Make a new request / Edit request info
    Request saveRequest(Request request);

    // 4. Close/reopen a request
    void closeRequest(int requestId);

    void processRequest(int requestId);

    void reopenRequest(int requestId);
}
