
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
 *         &lt;element name="siteUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="includeAllSubdomains" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
    "siteUrl",
    "includeAllSubdomains"
})
@XmlRootElement(name = "GetSiteRoles")
public class GetSiteRoles {

    @XmlElementRef(name = "siteUrl", namespace = "http://schemas.datacontract.org/2004/07/Microsoft.Bing.Webmaster.Api", type = JAXBElement.class, required = false)
    protected JAXBElement<String> siteUrl;
    protected Boolean includeAllSubdomains;

    /**
     * Gets the value of the siteUrl property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSiteUrl() {
        return siteUrl;
    }

    /**
     * Sets the value of the siteUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSiteUrl(JAXBElement<String> value) {
        this.siteUrl = value;
    }

    /**
     * Gets the value of the includeAllSubdomains property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeAllSubdomains() {
        return includeAllSubdomains;
    }

    /**
     * Sets the value of the includeAllSubdomains property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeAllSubdomains(Boolean value) {
        this.includeAllSubdomains = value;
    }

}
