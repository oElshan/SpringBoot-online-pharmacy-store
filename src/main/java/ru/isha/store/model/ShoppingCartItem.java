package ru.isha.store.model;

import com.fasterxml.jackson.annotation.JsonView;
import ru.isha.store.entity.Product;
import ru.isha.store.utils.Views;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable {

    private static final long serialVersionUID = 1689073980159902317L;
    @JsonView(Views.Public.class)
    private Product product;
    private int count;



    public ShoppingCartItem(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
