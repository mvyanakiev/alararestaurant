package alararestaurant.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "items")
public class Item extends BaseEntity {

    private String name;
    private Category category;
    private BigDecimal price;
    private List<OrderItem> orderItems;

    public Item() {
        this.orderItems = new ArrayList<>();
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id", nullable = false
    )
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @OneToMany(targetEntity = OrderItem.class, mappedBy = "item")
    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

//id – integer, Primary Key
//name – text with min length 3 and max length 30 (required, unique)
//category – the item’s category (required)
//price – decimal (non-negative, minimum value: 0.01, required)
//orderItems – collection of type OrderItem


//    @OneToMany(targetEntity = Card.class, mappedBy = "bankAccount")
//    public List<Card> getCards() {
//        return cards;
//    }
