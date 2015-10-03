
package fogetti.phish.storm.wsdl.bing.webmaster;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetActivePagePreviewBlocksResult" type="{http://schemas.datacontract.org/2004/07/Microsoft.Bing.Webmaster.Shared.DataContracts}ArrayOfPagePreview" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getActivePagePreviewBlocksResult"
})
@XmlRootElement(name = "GetActivePagePreviewBlocksResponse")
public class GetActivePagePreviewBlocksResponse {

    @XmlElementRef(name = "GetActivePagePreviewBlocksResult", namespace = "http://schemas.datacontract.org/2004/07/Microsoft.Bing.Webmaster.Api", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfPagePreview> getActivePagePreviewBlocksResult;

    /**
     * Gets the value of the getActivePagePreviewBlocksResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPagePreview }{@code >}
     *     
     */
    public JAXBElement<ArrayOfPagePreview> getGetActivePagePreviewBlocksResult() {
        return getActivePagePreviewBlocksResult;
    }

    /**
     * Sets the value of the getActivePagePreviewBlocksResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfPagePreview }{@code >}
     *     
     */
    public void setGetActivePagePreviewBlocksResult(JAXBElement<ArrayOfPagePreview> value) {
        this.getActivePagePreviewBlocksResult = value;
    }

}
