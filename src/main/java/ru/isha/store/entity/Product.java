package ru.isha.store.entity;

import com.fasterxml.jackson.annotation.JsonView;
import ru.isha.store.utils.Views;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String name;
    private Subcategory subcategory;
    private String description;
    private String imgLink;
    @JsonView(Views.Public.class)
    private BigDecimal price;
    private List<OrderItem> items;
    private Producer producer;
    private SpecCategory specCategory;
    private String visible;
    private Date date;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "visible")
    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_specCategory",foreignKey = @ForeignKey(name = "product_specCategory__fk"))
    public SpecCategory getSpecCategory() {
        return specCategory;
    }

    public void setSpecCategory(SpecCategory specCategory) {
        this.specCategory = specCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER ,cascade =  {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_producer",foreignKey = @ForeignKey(name = "product_producer__fk"))
    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subcategory",foreignKey = @ForeignKey(name = "product_subcategory__fk"))
    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "img_link")
    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id) &&
                name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return new StringBuilder("Product{").append("id=").append(id).append(", name=").append(name).toString();

    }
}

