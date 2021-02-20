package ru.isha.store.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class EditOrderItem {


    @NotEmpty
    @Pattern(regexp = "^\\d+$",message = "Error, only use integers")
    private String productCount;
    private long productId;
    private long orderId;

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }


    @Override
    public String toString() {
        return "EditOrderItem{" +
                "productCount=" + productCount +
                ", productId=" + productId +
                ", orderId=" + orderId +
                '}';
    }
}
