package ru.isha.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = -6256549125966558409L;
    private int id;
    private String name;
    private String url;
    private List<Subcategory> subcategoryList;

    public Category() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    public int getId() {
        return id;
    }

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    public List<Subcategory> getSubcategoryList() {
        return subcategoryList;
    }

    public void setSubcategoryList(List<Subcategory> subcategoryList) {
        this.subcategoryList = subcategoryList;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Category(CategoryBuilder categoryBuilder) {

        this.id = categoryBuilder.id;
        this.name = categoryBuilder.name;
        this.subcategoryList = categoryBuilder.subcategoryList;
        this.url = categoryBuilder.url;
    }

    public static class CategoryBuilder {
        private int id;
        private String name;
        private List<Subcategory> subcategoryList;
        private String url;

        public CategoryBuilder() {
        }


        public CategoryBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public CategoryBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CategoryBuilder setSubcategoryList(List<Subcategory> subcategoryList) {
            this.subcategoryList = subcategoryList;
            return this;
        }

        public Category build() {
            return new Category(this);
        }

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id &&
                Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
