package ru.isha.store.entity;

import com.fasterxml.jackson.annotation.JsonView;
import ru.isha.store.utils.Views;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = -7249074436745560142L;
    @JsonView(Views.Public.class)
    private Long id;
    @JsonView(Views.Public.class)
    private String name;
    private Subcategory subcategory;
    private String description;
    private String imgLink;
    @JsonView(Views.Public.class)
    private BigDecimal price;
    private Producer producer;
    private SpecCategory specCategory;
    private String visible;
    private Date date;

    public Product() {
    }


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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_subcategory",foreignKey = @ForeignKey(name = "product_subcategory__fk"))
    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
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

    public Product( ProductBuilder productBuilder) {
        this.id = productBuilder.id;
        this.name = productBuilder.name;
        this.subcategory = productBuilder.subcategory;
        this.description = productBuilder.description;
        this.imgLink = productBuilder.imgLink;
        this.price = productBuilder.price;
        this.producer = productBuilder.producer;
        this.specCategory = productBuilder.specCategory;
        this.visible = productBuilder.visible;
        this.date = productBuilder.date;
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private Subcategory subcategory;
        private String description;
        private String imgLink;
        private BigDecimal price;
        private Producer producer;
        private SpecCategory specCategory;
        private String visible;
        private Date date;

        public ProductBuilder() {
        }

        public ProductBuilder setProducer(Producer producer) {
            this.producer = producer;
            return this;
        }

        public ProductBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder setSubcategory(Subcategory subcategory) {
            this.subcategory = subcategory;
            return this;
        }

        public ProductBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder setImgLink(String imgLink) {
            this.imgLink = imgLink;
            return this;
        }

        public ProductBuilder setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder setSpecCategory(SpecCategory specCategory) {
            this.specCategory = specCategory;
            return this;
        }

        public ProductBuilder setVisible(String visible) {
            this.visible = visible;
            return this;
        }

        public ProductBuilder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Product build() {
            return  new Product(this);
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(subcategory, product.subcategory) &&
                Objects.equals(description, product.description) &&
                Objects.equals(imgLink, product.imgLink) &&
                Objects.equals(price, product.price) &&
                Objects.equals(producer, product.producer) &&
                Objects.equals(specCategory, product.specCategory) &&
                Objects.equals(visible, product.visible) &&
                Objects.equals(date, product.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, subcategory, description, imgLink,
                price, producer, specCategory, visible, date);
    }

    @Override
    public String toString() {
        return new StringBuilder("Product{").append("id=").append(id).append(", name=").append(name).toString();

    }
}

