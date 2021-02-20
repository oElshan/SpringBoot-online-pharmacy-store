package ru.isha.store.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EditOrder {

    @NotNull
    private Long id;
    @NotEmpty(message = "client name cannot be empty!")
    private String clientFirstName;
    @NotEmpty(message = "client name cannot be empty!")
    private String clientLastName;
    @NotEmpty(message = "client phone cannot be empty!")
    private String clientPhone;
    @Email
    private String clientEmail;
    @NotEmpty(message = "client address cannot be empty!")
    private String clientStreetAddress;
    @NotEmpty(message = "client address cannot be empty!")
    private String clientStreetTown;
    private String orderStatus;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientStreetAddress() {
        return clientStreetAddress;
    }

    public void setClientStreetAddress(String clientStreetAddress) {
        this.clientStreetAddress = clientStreetAddress;
    }

    public String getClientStreetTown() {
        return clientStreetTown;
    }

    public void setClientStreetTown(String clientStreetTown) {
        this.clientStreetTown = clientStreetTown;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "EditOrder{" +
                "id=" + id +
                ", clientFirstName='" + clientFirstName + '\'' +
                ", clientLastName='" + clientLastName + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", clientStreetAddress='" + clientStreetAddress + '\'' +
                ", clientStreetTown='" + clientStreetTown + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}

