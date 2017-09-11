package com.example.helloworld.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags = "Arithmetic Calculation")
public class ArithmeticController {
    @PostMapping(path = "/arith/add")
    @ApiOperation(value = "Add",
            nickname = "Arithmetic_add",
            notes = "Add two values.")
    public ArithmeticResponse add(@RequestBody ArithmeticRequest request) {
        return new ArithmeticResponse(request.getValue1() + request.getValue2());
    }

    @PostMapping(path = "/arith/sub")
    @ApiOperation(value = "Subtract",
            nickname = "Arithmetic_sub",
            notes = "Subtract one value from the other value.")
    public ArithmeticResponse sub(@RequestBody ArithmeticRequest request) {
        return new ArithmeticResponse(request.getValue1() - request.getValue2());
    }

    @PostMapping(path = "/arith/mul")
    @ApiOperation(value = "Multiple",
            nickname = "Arithmetic_mul",
            notes = "Multiply two values.")
    public ArithmeticResponse mul(@RequestBody ArithmeticRequest request) {
        return new ArithmeticResponse(request.getValue1() * request.getValue2());
    }

    @PostMapping(path = "/arith/div")
    @ApiOperation(value = "Divide",
            nickname = "Arithmetic_div",
            notes = "Divide one value by the other value.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request", response = BadRequestResponse.class)
    })
    public ArithmeticResponse div(@RequestBody ArithmeticRequest request) {
        return new ArithmeticResponse(request.getValue1() / request.getValue2());
    }

    @Value
    @ApiModel(description = "Common request model for arithmetic calculation.")
    public static class ArithmeticRequest {
        @NotNull
        @ApiModelProperty(notes = "Former operand.", example = "1")
        private final int value1;
        @NotNull
        @ApiModelProperty(notes = "Latter operand.", example = "2")
        private final int value2;
    }

    @Value
    @ApiModel(description = "Common response model for arithmetic calculation.")
    public static class ArithmeticResponse {
        @NotNull
        @ApiModelProperty(notes = "Result value.", example = "3")
        private final int result;
    }

    @Value
    @ApiModel(value = "ArithmeticController.BadRequestResponse",
            description = "Response model for bad request.")
    public static class BadRequestResponse {
        @NotNull
        @ApiModelProperty(notes = "Error message.", example = "0 is not permitted.")
        private final String message;
    }
}
