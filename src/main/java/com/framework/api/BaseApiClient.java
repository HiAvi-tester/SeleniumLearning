package com.framework.api;

import com.framework.config.ConfigReader;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/** Shared request specification: base URI, Allure request/response capture, logging on failure. */
public abstract class BaseApiClient {

    private static final RequestSpecification SPEC = new RequestSpecBuilder()
            .setBaseUri(ConfigReader.apiBaseUrl())
            .addFilter(new AllureRestAssured())
            .log(LogDetail.URI)
            .build();

    protected RequestSpecification request() {
        return given().spec(SPEC);
    }
}
