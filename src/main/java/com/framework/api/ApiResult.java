package com.framework.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * Automation Exercise always answers HTTP 200 and puts the real status in a "responseCode" field,
 * with a text/html content type. This wrapper parses the body as JSON regardless and exposes
 * both codes, so tests assert on the business status rather than the transport status.
 */
public record ApiResult(int httpStatus, int responseCode, JsonPath json, String rawBody) {

    public static ApiResult from(Response response) {
        String body = response.asString();
        JsonPath json = JsonPath.from(body);
        return new ApiResult(response.statusCode(), json.getInt("responseCode"), json, body);
    }

    public String message() {
        return json.getString("message");
    }
}
