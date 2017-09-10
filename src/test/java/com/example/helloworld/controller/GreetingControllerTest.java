package com.example.helloworld.controller;

import com.example.helloworld.configuration.RestDocsConfiguration;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
@AutoConfigureRestDocs(outputDir = "build/snippets", uriHost = "example.com", uriPort = 80)
@Import(RestDocsConfiguration.class)
@SuppressWarnings("SingleCharacterStringConcatenation")
public class GreetingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void top() throws Exception {
        val expectedContent = "" +
                "{" +
                "  \"alive\": true\n" +
                "}";

        mockMvc.perform(get("/")
                                .param("stage", "DEVELOPING")
                                .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedContent));
    }

    @Test
    public void greet() throws Exception {
        val requestContent = "" +
                "{\n" +
                "  \"title\": \"Mr.\",\n" +
                "  \"name\": \"dayflower\"" +
                "}\n";

        val expectedContent = "" +
                "{\n" +
                "  \"message\": \"Hello, Mr. dayflower!\"\n" +
                "}\n";

        mockMvc.perform(post("/greet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(requestContent))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedContent));
    }
}
