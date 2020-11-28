package com.pawelzabczynski

import com.pawelzabczynski.salesforce.SalesforceClient
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

import static io.restassured.RestAssured.baseURI
import static io.restassured.http.ContentType.XML
import static io.restassured.RestAssured.port

class BaseXMLTestSpecification extends Specification {
    private static final String PORT_NAME = "integration.test.port"
    private static final String USERNAME_NAME = "integration.test.username"
    private static final String PASSWORD_NAME = "integration.test.password"
    private static final String SECURITY_TOKEN_NAME = "integration.test.security_token"
    private final static DEFAULT_APPLICATION_PORT = "80"

    protected def client = new SalesforceClient()
    static String username
    static String password
    static String security_token

    def setupSpec() {
        baseURI = "https://login.salesforce.com"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
        if (getPropertyOrElse(PORT_NAME, null) != null) {
//            set only when port different then 80
            port = getPropertyOrElse(PORT_NAME, DEFAULT_APPLICATION_PORT).toInteger()
        }

        username = getPropertyOrElse(USERNAME_NAME, "None")
        password = getPropertyOrElse(PASSWORD_NAME, "None")
        security_token = getPropertyOrElse(SECURITY_TOKEN_NAME, "None")

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(XML)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build()
    }

    private static String getPropertyOrElse(String path, String fallback) {
        String result = System.getProperty(path)
        if(StringUtils.isNotBlank(result)) {
            return result
        }

        return fallback
    }

}
