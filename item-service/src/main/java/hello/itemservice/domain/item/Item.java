package hello.itemservice.domain.item;


import lombok.Getter;
import lombok.Setter;


//@Data
@Getter @Setter
public class Item {

    private Long id;
    private String itemName;
    private Integer price=0;
    private int quantity = 0;

    public Item() {
    }

    public Item(String itemName, Integer price, int quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    
}
