package alararestaurant.domain.dtos.ordersimportdto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlItemImportRootDto {

    @XmlElement(name = "item")
    private XmlItemImportDto[] xmlItemImportDtos;

    public XmlItemImportRootDto() {
    }

    public XmlItemImportDto[] getXmlItemImportDtos() {
        return this.xmlItemImportDtos;
    }

    public void setXmlItemImportDtos(XmlItemImportDto[] xmlItemImportDtos) {
        this.xmlItemImportDtos = xmlItemImportDtos;
    }

}
