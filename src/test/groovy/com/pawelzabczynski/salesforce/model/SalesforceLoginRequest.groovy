package com.pawelzabczynski.salesforce.model

import io.restassured.config.XmlConfig

import javax.xml.soap.MessageFactory
import javax.xml.soap.SOAPBody
import javax.xml.soap.SOAPElement
import javax.xml.soap.SOAPEnvelope
import javax.xml.soap.SOAPMessage
import javax.xml.soap.SOAPPart

class SalesforceLoginRequest {
/**
    <?xml version="1.0" encoding="utf-8" ?>
<env:Envelope xmlns:xsd="http://www.w3.org/2001/XMLSchema"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:env="http://schemas.xmlsoap.org/soap/envelope/">
  <env:Body>
    <n1:login xmlns:n1="urn:partner.soap.sforce.com">
      <n1:username>your_username</n1:username>
      <n1:password>your_password</n1:password>
    </n1:login>
  </env:Body>
</env:Envelope>
*/

    static String build(String username, String password, String securityToken) {

        MessageFactory messageFactory = MessageFactory.newInstance()
        SOAPMessage soapMessage = messageFactory.createMessage()
        SOAPPart soapPart = soapMessage.getSOAPPart()
        SOAPEnvelope envelope = soapPart.getEnvelope()
        SOAPBody soapBody = envelope.getBody()

        envelope.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema")
        envelope.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance")
        envelope.addNamespaceDeclaration("env", "http://schemas.xmlsoap.org/soap/envelope/")

        soapBody.addNamespaceDeclaration("n1", "urn:partner.soap.sforce.com")

        SOAPElement loginMsg = soapBody.addChildElement("login", "n1")
        loginMsg.addChildElement( "username", "n1").addTextNode(username)
        loginMsg.addChildElement( "password", "n1").addTextNode(password + securityToken)
        soapMessage.saveChanges()

        ByteArrayOutputStream out = new ByteArrayOutputStream()
        soapMessage.writeTo(out)
        String soapMessageInText = new String(out.toByteArray())

        return soapMessageInText
    }

    static XmlConfig xmlConfig() {
        return XmlConfig
                .xmlConfig()
                .namespaceAware(true)
                .declareNamespace("n1", "urn:partner.soap.sforce.com")
    }

}
