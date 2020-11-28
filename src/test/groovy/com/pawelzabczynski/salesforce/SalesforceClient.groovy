package com.pawelzabczynski.salesforce

import com.pawelzabczynski.salesforce.constances.SalesforceSoapApi
import com.pawelzabczynski.salesforce.login.SalesforceLoginResponseWrapper
import com.pawelzabczynski.salesforce.model.SalesforceLoginRequest
import io.restassured.config.HttpClientConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.config.XmlConfig
import io.restassured.response.Response
import org.apache.http.params.CoreConnectionPNames

import static io.restassured.RestAssured.*

class SalesforceClient {

    SalesforceLoginResponseWrapper sendLoginRequest(String un, String ps, String securityToken) {

        String xml = SalesforceLoginRequest.build(un, ps, securityToken)
        XmlConfig xmlConf = SalesforceLoginRequest.xmlConfig()
        RestAssuredConfig conf = RestAssuredConfig
                .newConfig()
                .xmlConfig(xmlConf)
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000))

        Response response = given()
                .config(conf)
                .header(SalesforceSoapApi.Login.header)
                .contentType(SalesforceSoapApi.Login.contentType)
                .body(xml)
                .when()
                .post(SalesforceSoapApi.Login.loginEndpoint)

        return new SalesforceLoginResponseWrapper(response)
    }

    private RestAssuredConfig requestConfig() {
        XmlConfig xmlConf = XmlConfig.xmlConfig().namespaceAware(true)

        return RestAssuredConfig
                .newConfig()
                .xmlConfig(xmlConf)
    }

}
