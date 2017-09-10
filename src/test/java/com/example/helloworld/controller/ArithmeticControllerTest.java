package com.example.helloworld.controller;

import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArithmeticController.class)
@AutoConfigureRestDocs(outputDir = "build/snippets", uriHost = "example.com", uriPort = 80)
public class ArithmeticControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void add() throws Exception {
        val requestContent = "" +
                "{\n" +
                "  \"value1\": 1,\n" +
                "  \"value2\": 2" +
                "}\n";

        val expectedContent = "" +
                "{\n" +
                "  \"result\": 3\n" +
                "}\n";

        mockMvc.perform(post("/arith/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestContent))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedContent))
               .andDo(document("Arithmetic_add"));
    }

    @Test
    public void sub() throws Exception {
        val requestContent = "" +
                "{\n" +
                "  \"value1\": 1,\n" +
                "  \"value2\": 2" +
                "}\n";

        val expectedContent = "" +
                "{\n" +
                "  \"result\": -1\n" +
                "}\n";

        mockMvc.perform(post("/arith/sub")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestContent))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedContent))
               .andDo(document("Arithmetic_sub"));
    }

    @Test
    public void mul() throws Exception {
        val requestContent = "" +
                "{\n" +
                "  \"value1\": 3,\n" +
                "  \"value2\": 5" +
                "}\n";

        val expectedContent = "" +
                "{\n" +
                "  \"result\": 15\n" +
                "}\n";

        mockMvc.perform(post("/arith/mul")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestContent))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedContent))
               .andDo(document("Arithmetic_mul"));
    }

    @Test
    public void div() throws Exception {
        val requestContent = "" +
                "{\n" +
                "  \"value1\": 35,\n" +
                "  \"value2\": 5" +
                "}\n";

        val expectedContent = "" +
                "{\n" +
                "  \"result\": 7\n" +
                "}\n";

        mockMvc.perform(post("/arith/div")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestContent))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedContent))
               .andDo(document("Arithmetic_div"));
    }
}
