package com.example.helloworld.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = "Greeting")
public class GreetingController {
    @GetMapping(path = "/")
    @ApiOperation(value = "Top",
            nickname = "GreetingControllerTest_top",
            notes = "Get availability of the service.")
    public TopResponse top(@RequestParam(defaultValue = "PRODUCTION") @NonNull
                           @ApiParam(required = true, value = "Stage.") Stage stage) {
        return new TopResponse(stage == Stage.DEVELOPING);
    }

    @PostMapping(path = "/greet")
    @ApiOperation(value = "Greet",
            nickname = "GreetingControllerTest_greet",
            notes = "Get greeting message for the request.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponse.class)
    })
    public GreetingResponse greet(@RequestBody @Valid @NonNull GreetingRequest request) {
        return new GreetingResponse(String.format("Hello, %s %s!", request.getTitle(), request.getName()));
    }

    enum Stage {
        DEVELOPING,
        STAGING,
        PRODUCTION
    }

    @Value
    @ApiModel(description = "Response model for top.")
    public static class TopResponse {
        @NotNull
        @ApiModelProperty(notes = "Whether service is available or not.")
        private final boolean alive;
    }

    @Value
    @ApiModel(description = "Request model for greeting.")
    public static class GreetingRequest {
        @NotNull
        @ApiModelProperty(notes = "Title of the person.", example = "Mr.")
        private final String title;
        @NotNull
        @ApiModelProperty(notes = "Name of the person.", example = "dayflower")
        private final String name;
    }

    @Value
    @ApiModel(description = "Response model for greeting.")
    public static class GreetingResponse {
        @NotNull
        @ApiModelProperty(notes = "Message from the system.", example = "Hello, Mr. dayflower")
        private final String message;
    }

    @Value
    @ApiModel(value = "GreetingController.BadRequestResponse",
            description = "Response model for bad request.")
    public static class BadRequestResponse {
        @NotNull
        @ApiModelProperty(notes = "Error message.", example = "0 is not permitted.")
        private final String message;
    }
}
