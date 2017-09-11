package com.example.helloworld;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.File;
import java.io.FileOutputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static springfox.documentation.builders.PathSelectors.regex;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class GenerateSwaggerOutputTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void generateSwaggerOutput() throws Exception {
        val fileName = System.getProperty("swaggerOutputFile");

        if (fileName == null) {
            log.warn("swaggerOutputFile property is not set, generating Swagger output file skipped.");
            return;
        }

        val outputFile = new File(fileName);

        mockMvc.perform(get("/v2/api-docs")
                                .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andDo(result -> {
                   //noinspection ResultOfMethodCallIgnored
                   outputFile.getParentFile().mkdirs();

                   try (val stream = new FileOutputStream(outputFile)) {
                       stream.write(result.getResponse().getContentAsByteArray());
                   }
               });
    }

    @TestConfiguration
    @EnableSwagger2
    @Import(BeanValidatorPluginsConfiguration.class)
    @SuppressWarnings("JUnitTestClassNamingConvention")
    static class SwaggerDocketConfiguration {
        @Bean
        public Docket documentation() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .useDefaultResponseMessages(false)
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(regex("^/(?!error).*$"))
                    .build()
                    .apiInfo(new ApiInfoBuilder().title("API Specification of Hello World")
                                                 .description("API document of Hello World service")
                                                 .contact(new Contact("dayflower",
                                                                      "https://github.com/dayflower",
                                                                      "@dayflower"))
                                                 .termsOfServiceUrl("http://example.com/")
                                                 .license("PUBLIC DOMAIN")
                                                 .version("1.0.0")
                                                 .build())
                    .tags(new Tag("Arithmetic Calculation", "Provides several basic arithmetic function."),
                          new Tag("Greeting", "Provides greeting methods."))
                    ;
        }
    }
}
