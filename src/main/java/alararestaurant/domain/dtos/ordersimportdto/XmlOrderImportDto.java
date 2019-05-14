package alararestaurant.domain.dtos.ordersimportdto;

import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlOrderImportDto {

    @XmlElement(name = "customer")
    private String customer;

    @XmlElement(name = "employee")
    private String employee;

    @XmlElement(name = "date-time")
    private String dateTime;

    @XmlElement(name = "type")
    private String type;

    @XmlElement(name = "items")
    private XmlItemImportRootDto xmlItemImportRootDtos;

    public XmlOrderImportDto() {
    }

    @NotNull
    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @NotNull
    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @NotNull
    public String getEmployee() {
        return this.employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @NotNull
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public XmlItemImportRootDto getXmlItemImportRootDtos() {
        return this.xmlItemImportRootDtos;
    }

    public void setXmlItemImportRootDtos(XmlItemImportRootDto xmlItemImportRootDtos) {
        this.xmlItemImportRootDtos = xmlItemImportRootDtos;
    }
}


// <customer>Garry</customer>
//    <employee>Maxwell Shanahan</employee>
//    <date-time>21/08/2017 13:22</date-time>
//    <type>ForHere</type>
//
//    <items>