package ru.isha.store.dto;

public class EditProductForm  extends NewProductForm {
    private Long id;
//    @NotEmpty(message = "Пустое поле! Введите имя клиента!")
//    @Size(max = 50,message = "max size  = 50  ")
//    private String productName;
//    private String producer;
//    @NotEmpty(message = "Пустое поле! Введите цену")
//    @Pattern(regexp = "^(\\d+)\\.?\\d+$", message = "Ошибка заполнения цены")
//    private String price;
//    private String description;
//    private String dateCreation;
//    private String category;
//    private String specCategory;
//    private String visible;
//    private MultipartFile photo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
