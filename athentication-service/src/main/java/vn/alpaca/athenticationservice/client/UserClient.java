package vn.alpaca.athenticationservice.client;

import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import vn.alpaca.athenticationservice.object.User;
import vn.alpaca.response.wrapper.SuccessResponse;

@Headers({
        "Accept: application/json; charset=utf-8",
        "Content-Type: application/json" })
//@FeignClient(name = "user-service", url = "http://localhost:8400/")
@FeignClient(value = "user-service")
public interface UserClient {

    @GetMapping(path = "/users/search/username")
    SuccessResponse<User> findByUserName(@RequestParam("val") String username);

    @GetMapping(path = "/users/{id}")
    SuccessResponse<User> findById(@PathVariable("id") int id);

}
