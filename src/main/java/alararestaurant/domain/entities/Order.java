package alararestaurant.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
public class Order extends BaseEntity{

    private String customer;
    private LocalDateTime dateTime;
    private Type type;
    private Employee employee;
    private List<OrderItem> orderItems;

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    @Column(name = "customer", nullable = false)
    public String getCustomer() {
        return this.customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "date_time", nullable = false)
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    @ManyToOne(targetEntity = Employee.class)
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id", nullable = false
    )
    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @OneToMany(targetEntity = OrderItem.class, mappedBy = "order")
    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

// id – integer, Primary Key
//customer – text (required)
//dateTime – date and time of the order (required)
//type – OrderType enumeration with possible values: “ForHere, ToGo (default: ForHere)” (required)
//employee – The employee who will process the order (required)
//orderItems – collection of type OrderItem
