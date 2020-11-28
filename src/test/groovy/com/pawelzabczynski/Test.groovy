package com.pawelzabczynski

import com.pawelzabczynski.salesforce.login.SalesforceLoginResponseWrapper
import static org.hamcrest.Matchers.*
import static org.hamcrest.MatcherAssert.assertThat

class Test extends BaseXMLTestSpecification {

    def "should return token"() {
        given:

        when:
        SalesforceLoginResponseWrapper response = client.sendLoginRequest(username, password, security_token)

        then:

        response.assertStatusIs2xx()
        assertThat response.body.serverUrl, not(is(null))
        assertThat response.body.sessionId, not(is(null))
    }

}
