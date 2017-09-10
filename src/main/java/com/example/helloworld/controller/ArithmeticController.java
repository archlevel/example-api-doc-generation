package com.example.helloworld.controller;

import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ArithmeticController {
    @PostMapping(path = "/arith/add")
    public ArithmeticResponse add(@RequestBody ArithmeticRequest request) {
        return new ArithmeticResponse(request.getValue1() + request.getValue2());
    }

    @PostMapping(path = "/arith/sub")
    public ArithmeticResponse sub(@RequestBody ArithmeticRequest request) {
        return new ArithmeticResponse(request.getValue1() - request.getValue2());
    }

    @PostMapping(path = "/arith/mul")
    public ArithmeticResponse mul(@RequestBody ArithmeticRequest request) {
        return new ArithmeticResponse(request.getValue1() * request.getValue2());
    }

    @PostMapping(path = "/arith/div")
    public ArithmeticResponse div(@RequestBody ArithmeticRequest request) {
        return new ArithmeticResponse(request.getValue1() / request.getValue2());
    }

    @Value
    public static class ArithmeticRequest {
        @NotNull
        private final int value1;
        @NotNull
        private final int value2;
    }

    @Value
    public static class ArithmeticResponse {
        @NotNull
        private final int result;
    }

    @Value
    public static class BadRequestResponse {
        @NotNull
        private final String message;
    }
}
