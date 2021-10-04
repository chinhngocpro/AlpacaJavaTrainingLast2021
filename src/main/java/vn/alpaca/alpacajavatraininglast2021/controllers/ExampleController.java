package vn.alpaca.alpacajavatraininglast2021.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/example")
public class ExampleController {

    @GetMapping
    public String example() {
        return "Success!";
    }
}
