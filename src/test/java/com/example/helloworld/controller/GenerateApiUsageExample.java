package com.example.helloworld.controller;

import com.example.helloworld.configuration.RestDocsConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AutoConfigureRestDocs(outputDir = "build/snippets", uriHost = "example.com", uriPort = 80)
@Import(RestDocsConfiguration.class)
public @interface GenerateApiUsageExample {
}
