package ru.isha.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "producer")
public class Producer implements Serializable {
    private static final long serialVersionUID = 689037798477322193L;
    private Long id;
    private String name;
    private List<Product> products;

    public Producer() {
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

    @OneToMany(mappedBy = "producer", fetch = FetchType.LAZY, cascade =  {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
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

    public Producer(ProducerBuilder producerBuilder) {
        this.id = producerBuilder.id;
        this.name = producerBuilder.name;
        this.products = producerBuilder.products;
    }

    public static class ProducerBuilder {
        private Long id;
        private String name;
        private List<Product> products;

        public ProducerBuilder() {
        }

        public ProducerBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ProducerBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ProducerBuilder setProducts(List<Product> products) {
            this.products = products;
            return this;
        }

        public  Producer build() {
            return new Producer(this);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return Objects.equals(id, producer.id) &&
                Objects.equals(name, producer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
