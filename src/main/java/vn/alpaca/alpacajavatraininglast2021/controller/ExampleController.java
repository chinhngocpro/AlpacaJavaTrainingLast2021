package vn.alpaca.alpacajavatraininglast2021.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/example")
public class ExampleController {

    @GetMapping
    public String example() {
        return "Success!";
    }

    @Secured({"USER_UPDATE"})
    @GetMapping("/test2")
    public String example1() {
        return "Success!2";
    }
}
