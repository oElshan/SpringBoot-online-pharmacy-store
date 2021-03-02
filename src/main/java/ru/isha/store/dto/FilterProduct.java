package ru.isha.store.dto;

import java.math.BigDecimal;
import java.util.Set;

public class FilterProduct {

    private String search;
    private Long id;
    private BigDecimal[] price;
    private Set<Long> producers;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Long> getProducers() {
        return producers;
    }

    public void setProducers(Set<Long> producers) {
        this.producers = producers;
    }

    public BigDecimal[] getPrice() {
        return price;
    }

    public void setPrice(BigDecimal[] price) {
        this.price = price;
    }


}
