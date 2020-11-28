package com.pawelzabczynski.salesforce.constances

import io.restassured.http.Header

class SalesforceSoapApi {
    static class Login {
        static loginEndpoint = "/services/Soap/u/50.0"
        static contentType = "text/xml; charset=UTF-8;"
        static header = new Header("SOAPAction", "login")
    }
}
