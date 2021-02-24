package ru.isha.store.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.isha.store.dto.EditProductForm;
import ru.isha.store.dto.FilterProduct;
import ru.isha.store.dto.NewProductForm;
import ru.isha.store.entity.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {

    Product findProductById(Long id);

    List <Product> findByNameContaining(String name);

    Page<Product> findProductBySearch(String name, BigDecimal[] price, Set<Long> producers, Pageable pageable);

    List<Subcategory> findAllSubCategory();

    List<Category> findAllCategory();

    List<Producer> findAllProducer();

    List<SpecCategory> findAllSpecCategory();

    Product editProduct(EditProductForm editProductForm);

    Product createProduct(NewProductForm productForm);

    List<Product> listAllProductsBySpecCategory(int id, Pageable pageable);

    Category findCategoryByUrl(String url);

     Page<Product> findAllProductBySubCategoryURL(String categoryURL, Pageable pageable);

    Map<String,BigDecimal> getMinMaxPriceProductByCategoryURL(String subcategory);

    Map<String,BigDecimal> getMinMaxPriceProductBySearchName(String search) ;

    List<Producer> getProducersBySearchProductName(String search);

    Product createOrEditProduct(Product product, final NewProductForm productForm);

    List<Producer> getProducersByCategoryURL(String categoryURL);

    Subcategory findSubcategoryByURL(String categoryURl);

    Page<Product> getProductByFilter(FilterProduct filterProduct, Pageable pageable);
}
