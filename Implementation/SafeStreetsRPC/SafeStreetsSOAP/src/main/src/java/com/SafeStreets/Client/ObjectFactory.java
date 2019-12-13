
package com.SafeStreets.Client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.SafeStreets.Client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UserRegistration_QNAME = new QName("http://SafeStreets.com/", "UserRegistration");
    private final static QName _UserRegistrationResponse_QNAME = new QName("http://SafeStreets.com/", "UserRegistrationResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.SafeStreets.Client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserRegistrationResponse }
     * 
     */
    public UserRegistrationResponse createUserRegistrationResponse() {
        return new UserRegistrationResponse();
    }

    /**
     * Create an instance of {@link UserRegistration }
     * 
     */
    public UserRegistration createUserRegistration() {
        return new UserRegistration();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserRegistration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "UserRegistration")
    public JAXBElement<UserRegistration> createUserRegistration(UserRegistration value) {
        return new JAXBElement<UserRegistration>(_UserRegistration_QNAME, UserRegistration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserRegistrationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://SafeStreets.com/", name = "UserRegistrationResponse")
    public JAXBElement<UserRegistrationResponse> createUserRegistrationResponse(UserRegistrationResponse value) {
        return new JAXBElement<UserRegistrationResponse>(_UserRegistrationResponse_QNAME, UserRegistrationResponse.class, null, value);
    }

}
