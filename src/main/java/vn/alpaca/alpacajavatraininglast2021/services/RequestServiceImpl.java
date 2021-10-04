package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exceptions.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Request;
import vn.alpaca.alpacajavatraininglast2021.repositories.RequestRepository;

import java.util.Collection;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public Collection<Request> findAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public Page<Request> findAllRequests(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }

    @Override
    public Collection<Request> findRequestsByNameContains(String name) {
        return requestRepository.findAllByNameContains(name);
    }

    @Override
    public Page<Request>
    findRequestsByNameContains(String name, Pageable pageable) {
        return requestRepository.findAllByNameContains(name, pageable);
    }

    @Override
    public Collection<Request>
    findRequestsByDescriptionContains(String description) {
        return requestRepository.findAllByDescriptionContains(description);
    }

    @Override
    public Page<Request>
    findRequestsByDescriptionContains(String description, Pageable pageable) {
        return requestRepository
                .findAllByDescriptionContains(description, pageable);
    }

    @Override
    public Collection<Request> findRequestsByCustomerId(int customerId) {
        return requestRepository.findAllByCustomerId(customerId);
    }

    @Override
    public Page<Request>
    findRequestsByCustomerId(int customerId, Pageable pageable) {
        return requestRepository.findAllByCustomerId(customerId, pageable);
    }

    @Override
    public Collection<Request> findRequestsByUserId(int userId) {
        return requestRepository.findAllByEmployeeInChargeId(userId);
    }

    @Override
    public Page<Request>
    findRequestsByUserId(int userId, Pageable pageable) {
        return requestRepository.findAllByEmployeeInChargeId(userId, pageable);
    }

    @Override
    public Collection<Request> findRequestsByStatus(String status) {
        return requestRepository.findAllByStatus(status);
    }

    @Override
    public Page<Request> findRequestsByStatus(String status,
                                              Pageable pageable) {
        return requestRepository.findAllByStatus(status, pageable);
    }

    @Override
    public Request findRequestById(int id) {
        return requestRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public void closeRequest(int requestId) {
        Request request = findRequestById(requestId);
        request.setStatus("CLOSED");
        saveRequest(request);
    }

    @Override
    public void processRequest(int requestId) {
        Request request = findRequestById(requestId);
        request.setStatus("PROCESSING");
        saveRequest(request);
    }

    @Override
    public void reopenRequest(int requestId) {
        Request request = findRequestById(requestId);
        request.setStatus("PENDING");
        saveRequest(request);
    }
}
