package ru.isha.store.services.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.isha.store.dto.EditProductForm;
import ru.isha.store.dto.NewProductForm;
import ru.isha.store.entity.*;
import ru.isha.store.repository.*;
import ru.isha.store.services.ProductService;
import ru.isha.store.utils.Constants;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepo productRepo;
    @Mock
    private ProducerRepo producerRepo;
    @Mock
    private SubCategoryRepo subCategoryRepo;
    @Mock
    private CategoryRepo categoryRepo;
    @Mock
    private SpecCategoryRepo specCategoryRepo;
    @Captor
    private ArgumentCaptor<Product> productCaptor;

    private ProductService productService;
    private Product product;
    private Category category;
    private Subcategory subcategory;
    private SpecCategory specCategory;
    private Producer producer;
    private PageRequest pageRequest;


    @BeforeEach

    void setUp() {

        specCategory = new SpecCategory.SpecCategoryBuilder()
                .setId(500)
                .setName("specCategory")
                .setProductList(Collections.singletonList(product))
                .build();

        producer = new Producer.ProducerBuilder()
                .setId(400L)
                .setName("producer")
                .setProducts(Collections.singletonList(product))
                .build();

        subcategory = new Subcategory.SubcategoryBuilder()
                .setId(100L)
                .setName("subcategory")
                .setCount(14)
                .setCategory(category)
                .setUrl("/subcategories")
                .setProducts(Collections.singletonList(product))
                .build();

        category = new Category.CategoryBuilder()
                .setId(200)
                .setName("category")
                .setUrl("/categories")
                .setSubcategoryList(Collections.singletonList(subcategory))
                .build();

        product = new Product.ProductBuilder()
                .setId(300L)
                .setName("product_name")
                .setPrice(new BigDecimal(100))
                .setDescription("description")
                .setImgLink("/link")
                .setVisible("on")
                .setSubcategory(subcategory)
                .setProducer(producer)
                .setSpecCategory(specCategory)
                .build();
        pageRequest = PageRequest.of(1, Constants.MAX_PRODUCTS_PER_HTML_PAGE);

        productService = new ProductServiceImpl(productRepo, producerRepo, subCategoryRepo, categoryRepo, specCategoryRepo);
    }


    @Test
    void findProductById() {
        when(productRepo.findProductById(300L)).thenReturn(product);
        Product actual = productService.findProductById(300L);
        assertThat(actual, equalTo(product));
    }


    @Test
    void findAllProductByCategoryURL() {
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
        when(productRepo.findBySubcategory_Url(subcategory.getUrl(),pageRequest)).thenReturn(productPage);
        Page<Product> actual = productService.findAllProductBySubCategoryURL(subcategory.getUrl(), pageRequest);
        assertThat(actual, contains(product));

    }

    @Test
    void findCategoryByUrl() {
        when(categoryRepo.findByUrl(category.getUrl())).thenReturn(Optional.of(category));
        Category actual = productService.findCategoryByUrl(category.getUrl());
        assertThat(actual, equalTo(category));
    }

    @Test
    void listAllSpecCategory() {
        when(specCategoryRepo.findAll()).thenReturn(Collections.singletonList(specCategory));
        List<SpecCategory> actual = productService.findAllSpecCategory();
        assertThat(actual, contains(specCategory));
    }

    @Test
    void findAllSubCategory() {
        when(subCategoryRepo.findAll()).thenReturn(Collections.singletonList(subcategory));
        List<Subcategory> actual = productService.findAllSubCategory();
        assertThat(actual, contains(subcategory));
    }

    @Test
    void listAllProductsBySpecCategory() {
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
        when(productRepo.findBySpecCategory_Id(specCategory.getId(), pageRequest)).thenReturn(productPage);
        List<Product> actual = productService.listAllProductsBySpecCategory(specCategory.getId(), pageRequest);
        assertThat(actual, contains(product));
    }

    @Test
    void findAllProducer() {
        when(producerRepo.findAll()).thenReturn(Collections.singletonList(producer));
        List<Producer> actual = productService.findAllProducer();
        assertThat(actual, contains(producer));
    }

    @Test
    void findAllCategory() {
        when(categoryRepo.findAll()).thenReturn(Collections.singletonList(category));
        List<Category> actual = productService.findAllCategory();
        assertThat(actual, contains(category));
    }


//    @Test
//    void findProductBySearch() {
//        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
//        when(productRepo.findByNameLike(product.getName(),pageRequest)).thenReturn(productPage);
//
//        when(productRepo.findByNameContainingAndPriceBetween(product.getName(), new BigDecimal(0),
//                new BigDecimal(100),pageRequest)).thenReturn(productPage);
//        when(productRepo.findByNameContainingAndProducer_IdIn(product.getName(),
//                Collections.singleton(producer.getId()), pageRequest)).thenReturn(productPage);
//        when(productRepo.findByNameContainingAndPriceBetweenAndProducer_IdIn(product.getName(), new BigDecimal(0),
//                new BigDecimal(100), Collections.singleton(producer.getId()), pageRequest)).thenReturn(productPage);
//
//        Page<Product> actual1 = productService.findProductBySearch(product.getName(),
//                new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(100)},
//                Collections.singleton(producer.getId()), pageRequest);
//        assertThat(actual1, contains(product));
//
//        Page<Product> actual2 = productService.findProductBySearch(product.getName(),
//                null, null, pageRequest);
//        assertThat(actual2, contains(product));
//
//        Page<Product> actual3 = productService.findProductBySearch(product.getName(),
//                new BigDecimal[]{BigDecimal.valueOf(0), BigDecimal.valueOf(100)}, null, pageRequest);
//        assertThat(actual3, contains(product));
//
//        Page<Product> actual4 = productService.findProductBySearch(product.getName(),
//               null,Collections.singleton(producer.getId()), pageRequest);
//        assertThat(actual4, contains(product));
//    }

    @Test
    void findByNameContaining() {
        when(productRepo.findByNameContaining(product.getName())).thenReturn(Collections.singletonList(product));
        List<Product> actual = productService.findByNameContaining(product.getName());
        assertThat(actual, contains(product));
    }

    @Test
    void editProduct() {
        EditProductForm editProductForm = new EditProductForm();
        editProductForm.setId(product.getId());
        editProductForm.setVisible(product.getVisible());
        editProductForm.setProductName(product.getName());
        editProductForm.setCategory(product.getSubcategory().getName());
        editProductForm.setDescription(product.getDescription());
        editProductForm.setPrice(product.getPrice().toString());
        editProductForm.setSpecCategory(product.getSpecCategory().getName());
        editProductForm.setProducer(product.getProducer().getName());

        when(productRepo.findProductById(editProductForm.getId())).thenReturn(product);
        when(subCategoryRepo.findByName(subcategory.getName())).thenReturn(subcategory);
        when(specCategoryRepo.findByName(specCategory.getName())).thenReturn(specCategory);
        when(producerRepo.findByName(producer.getName())).thenReturn(producer);

        Product actual = productService.editProduct(editProductForm);
        assertThat(actual, equalTo(product));
    }

    @Test
    void createProduct() {
        NewProductForm newProductForm = new NewProductForm();
        newProductForm.setVisible(product.getVisible());
        newProductForm.setProductName(product.getName());
        newProductForm.setCategory(product.getSubcategory().getName());
        newProductForm.setDescription(product.getDescription());
        newProductForm.setPrice(product.getPrice().toString());
        newProductForm.setSpecCategory(product.getSpecCategory().getName());
        newProductForm.setProducer(product.getProducer().getName());

        when(subCategoryRepo.findByName(subcategory.getName())).thenReturn(subcategory);
        when(specCategoryRepo.findByName(specCategory.getName())).thenReturn(specCategory);
        when(producerRepo.findByName(producer.getName())).thenReturn(producer);

        Product actual = productService.createProduct(newProductForm);
        assertThat(actual.getName(), equalTo(product.getName()));
    }

    @Test
    void createOrEdit_NullProduct() {
        NewProductForm newProductForm = new NewProductForm();
        newProductForm.setVisible(product.getVisible());
        newProductForm.setProductName(product.getName());
        newProductForm.setCategory(product.getSubcategory().getName());
        newProductForm.setDescription(product.getDescription());
        newProductForm.setPrice(product.getPrice().toString());
        newProductForm.setSpecCategory(product.getSpecCategory().getName());
        newProductForm.setProducer(product.getProducer().getName());
        Assertions.assertThrows(NullPointerException.class, () -> productService.createOrEditProduct(null, newProductForm));
    }

//    @Test
//    void getMinMaxPriceProductByCategoryURL() {
//
//        when(productRepo.getRangePriceByProduct_SubCategory(subcategory.getUrl())).thenReturn(
//                Collections.singletonList(new BigDecimal[]{BigDecimal.valueOf(1), BigDecimal.valueOf(200)}));
//
//        Map<String, BigDecimal> actual = productService.getMinMaxPriceProductByCategoryURL(subcategory.getUrl());
//
//        assertThat(actual.get("min"),equalTo(BigDecimal.valueOf(1)));
//        assertThat(actual,hasValue(BigDecimal.valueOf(200)));
//    }

    @Test
    void getMinMaxPriceProductBySearchName() {
        when(productRepo.getRangePriceByProduct_NameLike(product.getName())).
                thenReturn(Collections.singletonList(new BigDecimal[]{BigDecimal.valueOf(1), BigDecimal.valueOf(200)}));

        Map<String, BigDecimal> actual = productService.getMinMaxPriceProductBySearchName(product.getName());

        assertThat(actual.get("min"),equalTo(BigDecimal.valueOf(1)));
        assertThat(actual,hasValue(BigDecimal.valueOf(200)));

    }

    @Test
    void getProducersBySearchProduct() {
        when(productRepo.findDistinctByNameContaining(product.getName())).thenReturn(Collections.singletonList(product));
        List<Producer> actual = productService.getProducersBySearchProductName(product.getName());
        assertThat(actual, contains(product.getProducer()));
    }

//    @Test
//    void getProductByFilter() {
//        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
//        FilterProduct filterProduct = new FilterProduct();
//        filterProduct.setIdCategory(product.getSubcategory().getId());
//        filterProduct.setPrice(new BigDecimal[]{BigDecimal.valueOf(1), BigDecimal.valueOf(200)});
//
//        when(productRepo.findBySubcategory_IdAndPriceBetween(filterProduct.getIdCategory(), filterProduct.getPrice()[0],
//                filterProduct.getPrice()[1], pageRequest)).thenReturn(productPage);
//        Page<Product> actual1 = productService.getProductByFilter(filterProduct, pageRequest);
//        assertThat(actual1, contains(product));
//
//        filterProduct.setProducers(Collections.singleton(product.getProducer().getId()));
//        when(productRepo.findBySubcategory_IdAndPriceBetweenAndProducer_IdIn(
//                filterProduct.getIdCategory(), filterProduct.getPrice()[0], filterProduct.getPrice()[1],
//                filterProduct.getProducers(), pageRequest)).thenReturn(productPage);
//        Page<Product> actual2 = productService.getProductByFilter(filterProduct, pageRequest);
//        assertThat(actual2, contains(product));
//    }

    @Test
    void getProducersByCategoryURL() {
        when(productRepo.findAllBySubcategory_Url(category.getUrl())).thenReturn(Collections.singletonList(product));
        List<Producer> actual = productService.getProducersByCategoryURL(category.getUrl());
        assertThat(actual, contains(product.getProducer()));

    }


    @Test
    void findSubcategoryByURL() {
        when(subCategoryRepo.findByUrl(subcategory.getUrl())).thenReturn(subcategory);
        Subcategory actual = productService.findSubcategoryByURL(subcategory.getUrl());
        assertThat(actual, equalTo(subcategory));
    }
}
