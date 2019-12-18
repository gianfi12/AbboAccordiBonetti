
package com.SafeStreets.Client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "DispatcherService", targetNamespace = "http://SafeStreets.com/", wsdlLocation = "http://localhost:8080/SafeStreetsSOAP/DispatcherService?wsdl")
public class DispatcherService
    extends Service
{

    private final static URL DISPATCHERSERVICE_WSDL_LOCATION;
    private final static WebServiceException DISPATCHERSERVICE_EXCEPTION;
    private final static QName DISPATCHERSERVICE_QNAME = new QName("http://SafeStreets.com/", "DispatcherService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/SafeStreetsSOAP/DispatcherService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        DISPATCHERSERVICE_WSDL_LOCATION = url;
        DISPATCHERSERVICE_EXCEPTION = e;
    }

    public DispatcherService() {
        super(__getWsdlLocation(), DISPATCHERSERVICE_QNAME);
    }

    public DispatcherService(WebServiceFeature... features) {
        super(__getWsdlLocation(), DISPATCHERSERVICE_QNAME, features);
    }

    public DispatcherService(URL wsdlLocation) {
        super(wsdlLocation, DISPATCHERSERVICE_QNAME);
    }

    public DispatcherService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, DISPATCHERSERVICE_QNAME, features);
    }

    public DispatcherService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DispatcherService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Dispatcher
     */
    @WebEndpoint(name = "DispatcherPort")
    public Dispatcher getDispatcherPort() {
        return super.getPort(new QName("http://SafeStreets.com/", "DispatcherPort"), Dispatcher.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Dispatcher
     */
    @WebEndpoint(name = "DispatcherPort")
    public Dispatcher getDispatcherPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://SafeStreets.com/", "DispatcherPort"), Dispatcher.class, features);
    }

    private static URL __getWsdlLocation() {
        if (DISPATCHERSERVICE_EXCEPTION!= null) {
            throw DISPATCHERSERVICE_EXCEPTION;
        }
        return DISPATCHERSERVICE_WSDL_LOCATION;
    }

}