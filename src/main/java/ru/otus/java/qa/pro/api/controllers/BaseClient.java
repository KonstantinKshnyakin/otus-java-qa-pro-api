package ru.otus.java.qa.pro.api.controllers;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.List;

public abstract class BaseClient {

    private final String baseUrl = System.getProperty("base.url", "https://petstore.swagger.io/v2");
    protected RequestSpecification requestSpec = new RequestSpecBuilder()
            .addFilters(
                    List.of(
                            new AllureRestAssured(),
                            new RequestLoggingFilter(),
                            new ResponseLoggingFilter()
                    )
            )
            .setBaseUri(baseUrl)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .build();

}
