package com.pawelzabczynski.salesforce

import io.restassured.response.Response

import static org.hamcrest.Matchers.allOf
import static org.hamcrest.Matchers.greaterThanOrEqualTo
import static org.hamcrest.Matchers.isEmptyOrNullString
import static org.hamcrest.Matchers.lessThan
import static org.hamcrest.Matchers.not
import static org.hamcrest.Matchers.*

class AbstractResponseWrapper {
    protected Response rawResponse

    def assertStatusIs2xx() {
        rawResponse.then().statusCode(isIn([200, 201]))
    }

    def assertStatusIs4xx() {
        rawResponse.then().statusCode(allOf(greaterThanOrEqualTo(400), lessThan(500)))
    }

    def assertErrorMessageIsPresent() {
        rawResponse.then().body("message", not(isEmptyOrNullString()))
    }

    int status() {
        rawResponse.statusCode()
    }
}
