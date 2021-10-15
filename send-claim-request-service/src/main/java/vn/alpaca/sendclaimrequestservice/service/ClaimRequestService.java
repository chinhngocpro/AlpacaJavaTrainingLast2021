package vn.alpaca.sendclaimrequestservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.alpaca.sendclaimrequestservice.client.CustomerFeignClient;
import vn.alpaca.sendclaimrequestservice.object.entity.ClaimRequest;
import vn.alpaca.sendclaimrequestservice.object.mapper.ClaimRequestMapper;
import vn.alpaca.sendclaimrequestservice.object.wrapper.request.ClaimRequestForm;
import vn.alpaca.sendclaimrequestservice.object.wrapper.response.ClaimRequestDTO;
import vn.alpaca.sendclaimrequestservice.repository.ClaimRequestRepository;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.stream.Collectors;

@Service
public class ClaimRequestService {

    private final ClaimRequestRepository repository;
    private final ClaimRequestMapper mapper;

    private final CustomerFeignClient customerClient;

    private final SaveImageService imageService;

    private final RateLimiterRegistry registry;

    public ClaimRequestService(
            ClaimRequestRepository repository,
            ClaimRequestMapper mapper,
            CustomerFeignClient customerClient,
            SaveImageService imageService,
            RateLimiterRegistry registry) {
        this.repository = repository;
        this.mapper = mapper;
        this.customerClient = customerClient;
        this.imageService = imageService;
        this.registry = registry;
    }

    @PostConstruct
    public void init() {
        io.github.resilience4j.ratelimiter.RateLimiter.EventPublisher
                eventPublisher =
                registry
                        .rateLimiter("sendRequest")
                        .getEventPublisher();
        eventPublisher.onSuccess(System.out::println);
        eventPublisher.onFailure(System.out::println);
    }

    @CircuitBreaker(name = "sendRequest", fallbackMethod = "fallbackSendRequest")
    @RateLimiter(name = "sendRequest")
    @Retry(name = "sendRequest")
    public ClaimRequestDTO sendRequest(ClaimRequestForm formData) {
        int customerId = customerClient
                .getByIdCardNumber(formData.getIdCardNumber())
                .getData().getId();
        System.out.println("SUCCESS!");
        ClaimRequest request = mapper.convertToEntity(formData);

        if (!ObjectUtils.isEmpty(formData.getReceiptPhotoFiles())) {
            request.setReceiptPhotos(formData.getReceiptPhotoFiles().stream()
                    .map(imageService::saveFile)
                    .filter(s -> !ObjectUtils.isEmpty(s))
                    .collect(Collectors.toList()));
        }

        request.setCustomerId(customerId);

        return mapper.covertToDTO(
                repository.save(request)
        );
    }

    public ClaimRequestDTO
    fallbackSendRequest(ClaimRequestForm formData, Throwable throwable) {
        System.out.println("Failed on: " + throwable);
        return new ClaimRequestDTO();
    }

    void updateRateLimits(
            String rateLimiterName,
            int limitPorPeriod,
            Duration timeoutDuration
    ) {
        io.github.resilience4j.ratelimiter.RateLimiter limiter =
                registry.rateLimiter(rateLimiterName);
        limiter.changeLimitForPeriod(limitPorPeriod);
        limiter.changeTimeoutDuration(timeoutDuration);
    }

}
