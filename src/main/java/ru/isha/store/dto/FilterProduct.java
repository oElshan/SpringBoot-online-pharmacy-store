package ru.isha.store.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

public class FilterProduct {

    private Long idCategory;
    private BigDecimal[] price;
    private Set<Long> producers;


    public Set<Long> getProducers() {
        return producers;
    }

    public void setProducers(Set<Long> producers) {
        this.producers = producers;
    }


    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public BigDecimal[] getPrice() {
        return price;
    }

    public void setPrice(BigDecimal[] price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "FilterProduct{" +
                "idCategory=" + idCategory +
                ", price=" + Arrays.toString(price) +
                '}';
    }
}
