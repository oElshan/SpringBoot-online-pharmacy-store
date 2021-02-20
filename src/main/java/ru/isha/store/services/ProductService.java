package ru.isha.store.services;


import org.springframework.data.domain.Page;
import ru.isha.store.dto.EditProductForm;
import ru.isha.store.dto.FilterProduct;
import ru.isha.store.dto.NewProductForm;
import ru.isha.store.entity.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {

    Product findProductById(Long id);

    List <Product> findByNameContaining(String name);

    Page<Product> findProductBySearch(String name, BigDecimal[] price, Set<Long> producers, int page, int limit);

    List<Subcategory> findAllSubCategory();

    List<Category> findAllCategory();

    List<Producer> findAllProducer();

    List<SpecCategory> listAllSpecCategory();

    Product editProduct(EditProductForm editProductForm) throws IOException;

    Product createProduct(NewProductForm productForm);

    List<Product> listAllProductsForSpecCategory(int id, int page, int limit);

    Category findCategoryByUrl(String url);

     Page<Product> findAllProductByCategoryURL(String categoryURL, int page, int limit);

    Map<String,BigDecimal> getMinMaxPriceProductByCategoryURL(String name);

    Map<String,BigDecimal> getMinMaxPriceProductBySearchName(String search) ;

    List<Producer> getProducersBySearchProduct(String search);

    Product createOrEditProduct(Product product, final NewProductForm productForm);

    List<Producer> getProducersByCategoryURL(String categoryURL);

    Subcategory findSubcategoryByURL(String categoryURl);

    Page<Product> findProductByCategoryIDWherePriceAndProducer(long categoryId, BigDecimal min, BigDecimal max, Set<Long> producerId, int page, int limit);

    Page<Product> getProductByFilter(FilterProduct filterProduct, int page, int maxProductsPerHtmlPage);
}
