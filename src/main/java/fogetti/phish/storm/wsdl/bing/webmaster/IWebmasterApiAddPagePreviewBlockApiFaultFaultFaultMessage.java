
package fogetti.phish.storm.wsdl.bing.webmaster;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "ApiFault", targetNamespace = "http://schemas.datacontract.org/2004/07/Microsoft.Bing.Webmaster.Api")
public class IWebmasterApiAddPagePreviewBlockApiFaultFaultFaultMessage
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private ApiFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public IWebmasterApiAddPagePreviewBlockApiFaultFaultFaultMessage(String message, ApiFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public IWebmasterApiAddPagePreviewBlockApiFaultFaultFaultMessage(String message, ApiFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: fogetti.phish.storm.wsdl.bing.webmaster.ApiFault
     */
    public ApiFault getFaultInfo() {
        return faultInfo;
    }

}