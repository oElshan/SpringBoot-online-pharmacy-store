package ru.isha.store.entity;

import com.fasterxml.jackson.annotation.JsonView;
import ru.isha.store.utils.Views;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = -96906528035960314L;
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private int count;
    private ClientOrder clientOrder;
    @JsonView(Views.Public.class)
    private Product product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_order",foreignKey = @ForeignKey(name = "orderItem_clientOrder_fk"))
    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product",foreignKey = @ForeignKey(name = "fk_item_product"))
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Basic
    @Column(name = "count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return count == orderItem.count &&
                Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", count=" + count +
                ", product=" + product.getId() +
                '}';
    }
}
