package ru.isha.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subcategory")
public class Subcategory implements Serializable {
    private static final long serialVersionUID = -79115048079649640L;
    private Long id;
    private String name;
    private Integer count;
    private String url;
    private List<Product> products;
    private Category category;

    public Subcategory() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category",foreignKey = @ForeignKey(name = "subcategory_category__fk"))
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "subcategory", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Subcategory(SubcategoryBuilder subcategoryBuilder) {
        this.id = subcategoryBuilder.id;
        this.name = subcategoryBuilder.name;
        this.count = subcategoryBuilder.count;
        this.url = subcategoryBuilder.url;
        this.products = subcategoryBuilder.products;
        this.category = subcategoryBuilder.category;
    }

    public static class SubcategoryBuilder {
        private Long id;
        private String name;
        private Integer count;
        private String url;
        private List<Product> products;
        private Category category;

        public SubcategoryBuilder() {
        }

        public SubcategoryBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public SubcategoryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public SubcategoryBuilder setCount(Integer count) {
            this.count = count;
            return this;
        }

        public SubcategoryBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public SubcategoryBuilder setProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public SubcategoryBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public Subcategory build() {
            return new Subcategory(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subcategory that = (Subcategory) o;
        return id.equals(that.id) &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
