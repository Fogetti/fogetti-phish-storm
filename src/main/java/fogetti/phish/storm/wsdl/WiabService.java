
package fogetti.phish.storm.wsdl;

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
@WebServiceClient(name = "WiabService", targetNamespace = "http://schemas.microsoft.com/research/2011/08/wiab", wsdlLocation = "http://weblm.research.microsoft.com/wiab.svc?singleWsdl")
public class WiabService
    extends Service
{

    private final static URL WIABSERVICE_WSDL_LOCATION;
    private final static WebServiceException WIABSERVICE_EXCEPTION;
    private final static QName WIABSERVICE_QNAME = new QName("http://schemas.microsoft.com/research/2011/08/wiab", "WiabService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://weblm.research.microsoft.com/wiab.svc?singleWsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WIABSERVICE_WSDL_LOCATION = url;
        WIABSERVICE_EXCEPTION = e;
    }

    public WiabService() {
        super(__getWsdlLocation(), WIABSERVICE_QNAME);
    }

    public WiabService(WebServiceFeature... features) {
        super(__getWsdlLocation(), WIABSERVICE_QNAME, features);
    }

    public WiabService(URL wsdlLocation) {
        super(wsdlLocation, WIABSERVICE_QNAME);
    }

    public WiabService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WIABSERVICE_QNAME, features);
    }

    public WiabService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WiabService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IWiabService
     */
    @WebEndpoint(name = "BasicHttpBinding_IWiabService")
    public IWiabService getBasicHttpBindingIWiabService() {
        return super.getPort(new QName("http://schemas.microsoft.com/research/2011/08/wiab", "BasicHttpBinding_IWiabService"), IWiabService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IWiabService
     */
    @WebEndpoint(name = "BasicHttpBinding_IWiabService")
    public IWiabService getBasicHttpBindingIWiabService(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.microsoft.com/research/2011/08/wiab", "BasicHttpBinding_IWiabService"), IWiabService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WIABSERVICE_EXCEPTION!= null) {
            throw WIABSERVICE_EXCEPTION;
        }
        return WIABSERVICE_WSDL_LOCATION;
    }

}
