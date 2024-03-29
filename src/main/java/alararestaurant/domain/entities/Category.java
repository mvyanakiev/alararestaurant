package alararestaurant.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "categories")
public class Category extends BaseEntity {

    private String name;
    private List<Item> items;

    public Category() {
        this.items = new ArrayList<>();
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = Item.class, mappedBy = "category")
    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

//id – integer, Primary Key
//name – text with min length 3 and max length 30 (required)
//items – collection of type Item