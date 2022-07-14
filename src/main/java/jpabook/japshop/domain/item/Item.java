package jpabook.japshop.domain.item;

import jpabook.japshop.domain.Category;
import jpabook.japshop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuntity;

    @ManyToMany(mappedBy = "items")
    private List<Category> catagories = new ArrayList<>();

    //비즈니스 로직//
    /**
     * stock 증가
     */
    public void addStock(int quantity){
        this.stockQuntity += quantity;
    }
    /**
     * stock 감소
     */
    public void removeStock(int quantity){
        int restStock = this.stockQuntity - quantity;

        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuntity = restStock;
    }
}
