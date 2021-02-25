package ru.isha.store.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditProductForm  extends NewProductForm {
    private Long id;
    @NotEmpty(message = "Пустое поле! Введите имя клиента!")
    @Size(max = 50,message = "max size  = 50  ")
    private String productName;
    private String producer;
    @NotEmpty(message = "Пустое поле! Введите цену")
    @Pattern(regexp = "^(\\d+)\\.?\\d+$", message = "Ошибка заполнения цены")
    private String price;
    private String description;
    private String dateCreation;
    private String category;
    private String specCategory;
    private String visible;
    private MultipartFile photo;

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String getProducer() {
        return producer;
    }

    @Override
    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public String getPrice() {
        return price;
    }

    @Override
    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDateCreation() {
        return dateCreation;
    }

    @Override
    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getSpecCategory() {
        return specCategory;
    }

    @Override
    public void setSpecCategory(String specCategory) {
        this.specCategory = specCategory;
    }

    @Override
    public String getVisible() {
        return visible;
    }

    @Override
    public void setVisible(String visible) {
        this.visible = visible;
    }

    @Override
    public MultipartFile getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
