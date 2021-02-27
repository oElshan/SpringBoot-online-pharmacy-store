package ru.isha.store.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ClientOrderDTO {

    @NotEmpty(message = "Пустое поле! Введите имя клиента!")
    @Size(max = 50,message = "max size  = 50  ")
    private String firstName;
    @NotEmpty(message = "Пустое поле! Введите имя клиента!")
    @Size(max = 50,message = "max size  = 50  ")
    private String lastName;
    @NotEmpty(message = "Пустое поле! Введите имя клиента!")
    @Size(max = 200,message = "max size  = 200  ")
    private String streetAddress;
    @NotEmpty(message = "Пустое поле! Введите имя клиента!")
    @Size(max = 50,message = "max size  = 50  ")
    private String town;
    private String zipCode;
    @Email
    private String email;
    @NotEmpty(message = "Пустое поле! Введите номер телефона!")
    @Pattern(regexp = "^([0-9]+)$", message = "Ошибка заполнения номера")
    private String phone;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ClientOrderDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", town='" + town + '\'' +
                ", zipCode=" + zipCode +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
