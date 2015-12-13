
package fogetti.phish.storm.wsdl.bing.webmaster;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
@WebServiceClient(name = "WebmasterApi", targetNamespace = "http://schemas.datacontract.org/2004/07/Microsoft.Bing.Webmaster.Api", wsdlLocation = "classpath:bing.webmasters.wsdl.xml")
public class WebmasterApi
    extends Service
{

    private final static URL WEBMASTERAPI_WSDL_LOCATION;
    private final static WebServiceException WEBMASTERAPI_EXCEPTION;
    private final static QName WEBMASTERAPI_QNAME = new QName("http://schemas.datacontract.org/2004/07/Microsoft.Bing.Webmaster.Api", "WebmasterApi");

    static {
        URL url = null;
        WebServiceException e = null;
        ClassLoader loader = WebmasterApi.class.getClassLoader();
        try {
			url = loader.getResource("bing.webmasters.wsdl.xml").toURI().toURL();
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        } catch (URISyntaxException ex) {
            e = new WebServiceException(ex);
		}
        WEBMASTERAPI_WSDL_LOCATION = url;
        WEBMASTERAPI_EXCEPTION = e;
    }

    public WebmasterApi() {
        super(__getWsdlLocation(), WEBMASTERAPI_QNAME);
    }

    public WebmasterApi(WebServiceFeature... features) {
        super(__getWsdlLocation(), WEBMASTERAPI_QNAME, features);
    }

    public WebmasterApi(URL wsdlLocation) {
        super(wsdlLocation, WEBMASTERAPI_QNAME);
    }

    public WebmasterApi(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WEBMASTERAPI_QNAME, features);
    }

    public WebmasterApi(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WebmasterApi(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IWebmasterApi
     */
    @WebEndpoint(name = "BasicHttpBinding_IWebmasterApi")
    public IWebmasterApi getBasicHttpBindingIWebmasterApi() {
        return super.getPort(new QName("http://schemas.datacontract.org/2004/07/Microsoft.Bing.Webmaster.Api", "BasicHttpBinding_IWebmasterApi"), IWebmasterApi.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IWebmasterApi
     */
    @WebEndpoint(name = "BasicHttpBinding_IWebmasterApi")
    public IWebmasterApi getBasicHttpBindingIWebmasterApi(WebServiceFeature... features) {
        return super.getPort(new QName("http://schemas.datacontract.org/2004/07/Microsoft.Bing.Webmaster.Api", "BasicHttpBinding_IWebmasterApi"), IWebmasterApi.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WEBMASTERAPI_EXCEPTION!= null) {
            throw WEBMASTERAPI_EXCEPTION;
        }
        return WEBMASTERAPI_WSDL_LOCATION;
    }

}
