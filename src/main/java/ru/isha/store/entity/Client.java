package ru.isha.store.entity;

import com.fasterxml.jackson.annotation.JsonView;
import ru.isha.store.utils.Views;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "client")
public class Client implements Serializable {
    private static final long serialVersionUID = 5140188785955589492L;
    @JsonView(Views.Public.class)
    private String firstName;
    @JsonView(Views.Public.class)
    private String lastName;
    @JsonView(Views.Public.class)
    private String streetAddress;
    @JsonView(Views.Public.class)
    private String town;
    @JsonView(Views.Public.class)
    private String email;
    @JsonView(Views.Public.class)
    private String phone;
    @JsonView(Views.Public.class)
    private Long id;
    private List<ClientOrder> clientOrders;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    public List<ClientOrder> getClientOrders() {
        return clientOrders;
    }

    public void setClientOrders(List<ClientOrder> clientOrders) {
        this.clientOrders = clientOrders;
    }

    @Basic
    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "streetAddress")
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Basic
    @Column(name = "town")
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id &&
                Objects.equals(firstName, client.firstName) &&
                Objects.equals(lastName, client.lastName) &&
                Objects.equals(streetAddress, client.streetAddress) &&
                Objects.equals(town, client.town) &&
                Objects.equals(email, client.email) &&
                Objects.equals(phone, client.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, streetAddress, town, email, phone, id);
    }
}
