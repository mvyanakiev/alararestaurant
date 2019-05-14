package alararestaurant.domain.dtos.ordersimportdto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlOrdersImportRootDto {

    @XmlElement(name = "order")
    private XmlOrderImportDto[] xmlOrderImportDto;

    public XmlOrdersImportRootDto() {
    }

    public XmlOrderImportDto[] getXmlOrderImportDto() {
        return this.xmlOrderImportDto;
    }

    public void setXmlOrderImportDto(XmlOrderImportDto[] xmlOrderImportDto) {
        this.xmlOrderImportDto = xmlOrderImportDto;
    }
}
