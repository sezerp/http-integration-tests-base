package com.pawelzabczynski.salesforce.login

import com.pawelzabczynski.salesforce.AbstractResponseWrapper
import io.restassured.response.Response

class SalesforceLoginResponseWrapper extends AbstractResponseWrapper {

    SalesforceLoginResponseWrapper(Response response) {
        this.rawResponse = response
    }

    SalesforceLoginResponse getBody() {
        String serverUrl = rawResponse.getBody().path("Envelope.Body.loginResponse.result.serverUrl.text()")
        String sessionId = rawResponse.getBody().path("Envelope.Body.loginResponse.result.sessionId.text()")

        return new SalesforceLoginResponse(serverUrl, sessionId)
    }

    class SalesforceLoginResponse {
        final String serverUrl
        final String sessionId

        SalesforceLoginResponse(String serverUrl, String sessionId) {
            this.serverUrl = serverUrl
            this.sessionId = sessionId
        }

    }
}
