package ru.isha.store.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "spec_category")
public class SpecCategory implements Serializable {
    private static final long serialVersionUID = -7899168931457588266L;
    private int id;
    private String name;
    private List<Product> productList;


    public SpecCategory() {
    }

    @OneToMany(mappedBy = "specCategory",fetch = FetchType.LAZY,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SpecCategory(SpecCategoryBuilder specCategoryBuilder) {
        this.id = specCategoryBuilder.id;
        this.name =specCategoryBuilder.name;
        this.productList = specCategoryBuilder.productList;
    }
    public static class SpecCategoryBuilder {
        private int id;
        private String name;
        private List<Product> productList;

        public SpecCategoryBuilder() {
        }


        public SpecCategoryBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public SpecCategoryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public SpecCategoryBuilder setProductList(List<Product> productList) {
            this.productList = productList;
            return this;
        }

        public SpecCategory build() {

            return new SpecCategory(this);
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecCategory that = (SpecCategory) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
