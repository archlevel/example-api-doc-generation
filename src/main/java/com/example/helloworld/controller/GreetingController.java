package com.example.helloworld.controller;

import lombok.NonNull;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class GreetingController {
    @GetMapping(path = "/")
    public TopResponse top(@RequestParam(defaultValue = "PRODUCTION") @NonNull Stage stage) {
        return new TopResponse(stage == Stage.DEVELOPING);
    }

    @PostMapping(path = "/greet")
    public GreetingResponse greet(@RequestBody @Valid @NonNull GreetingRequest request) {
        return new GreetingResponse(String.format("Hello, %s %s!", request.getTitle(), request.getName()));
    }

    enum Stage {
        DEVELOPING,
        STAGING,
        PRODUCTION
    }

    @Value
    public static class TopResponse {
        @NotNull
        private final boolean alive;
    }

    @Value
    public static class GreetingRequest {
        @NotNull
        private final String title;
        @NotNull
        private final String name;
    }

    @Value
    public static class GreetingResponse {
        @NotNull
        private final String message;
    }

    @Value
    public static class BadRequestResponse {
        @NotNull
        private final String message;
    }
}
