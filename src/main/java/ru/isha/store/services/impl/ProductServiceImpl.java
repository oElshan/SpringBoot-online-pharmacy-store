package ru.isha.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.isha.store.dto.EditProductForm;
import ru.isha.store.dto.FilterProduct;
import ru.isha.store.dto.NewProductForm;
import ru.isha.store.entity.*;
import ru.isha.store.exception.ResourceNotFoundException;
import ru.isha.store.repository.*;
import ru.isha.store.services.ProductService;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
// TODO: 2020-12-26 добавить абстрактный класс для сервиса
// TODO: 2020-12-26 реализовать метод потчета максимальной и мнимианльной цены для товаров по поиску имени


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ProducerRepo producerRepo;

    @Autowired
    SubCategoryRepo subCategoryRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    SpecCategoryRepo specCategoryRepo;
    @Autowired
    Environment env;

    @Override
    public Page<Product> findAllProductByCategoryURL(String categoryName, int page, int limit) {

        return productRepo.findBySubcategory_Url(categoryName, PageRequest.of(page-1,limit));
    }

    @Override
    public Category findCategoryByUrl(String url) {
        return categoryRepo.findByUrl(url);
    }

    @Override
    public List<SpecCategory> listAllSpecCategory() {
        return specCategoryRepo.findAll();
    }
    @Override
    public Product findProductById(Long id) {
        return productRepo.findProductById(id);
    }

    @Override
    public List<Subcategory> findAllSubCategory() {
        return subCategoryRepo.findAll();
    }

    public List<Product> listAllProductsForSpecCategory(int id,int page, int limit) {
      return  productRepo.findBySpecCategory_Id(id, PageRequest.of(page - 1, limit)).getContent();
    }

    @Override
    public List<Producer> findAllProducer() {
        return producerRepo.findAll();
    }

    @Override
    public List<Category> findAllCategory() {
        return categoryRepo.findAll();
    }

    @Override
    public Page<Product> findProductBySearch(String name, BigDecimal[] price, Set<Long> producers, int page, int limit) {

        if (price == null && producers == null) {

            return productRepo.findByNameLike(name, PageRequest.of(page - 1, limit));

        } else if (price != null && producers == null) {

            return productRepo.findByNameContainingAndPriceBetween(name, price[0], price[1], PageRequest.of(page - 1, limit));

        } else if (price == null && producers != null) {

            return productRepo.findByNameContainingAndProducer_IdIn(name,  producers, PageRequest.of(page - 1, limit));

        } else {
            return productRepo.findByNameContainingAndPriceBetweenAndProducer_IdIn(name, price[0], price[1], producers, PageRequest.of(page - 1, limit));
        }

    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return productRepo.findByNameContaining(name, PageRequest.of(1,5));
    }

    @Override
    @Transactional
    public Product editProduct(EditProductForm editProductForm)  {
        Product product =  productRepo.findProductById(editProductForm.getId());
        createOrEditProduct(product, editProductForm);
        return product;
    }

    @Transactional
    @Override

    public Product createProduct(NewProductForm productForm) {
        Product product = new Product();
        createOrEditProduct(product, productForm);
        return product;
    }

    public Product createOrEditProduct( Product product, final NewProductForm productForm) {

        product.setName(productForm.getProductName());
        product.setDescription(productForm.getDescription());
        product.setPrice(new BigDecimal(productForm.getPrice()));
        Subcategory subcategory = subCategoryRepo.findByName(productForm.getCategory());
        product.setSubcategory(subcategory);
        SpecCategory specCategory = specCategoryRepo.findByName(productForm.getSpecCategory());
        product.setSpecCategory(specCategory);
        product.setVisible(productForm.getVisible());

        MultipartFile file = productForm.getPhoto();
        uploadImgProduct(file, product);

        Producer producer = producerRepo.findByName(productForm.getProducer());
        if (producer == null) {
            producer = new Producer();
            producer.setName(productForm.getProducer());

        }
        producer.setProducts(new ArrayList<Product>(Arrays.asList(product)));
        product.setProducer(producer);
        productRepo.save(product);
        return product;

    }


    @Override
    public Map<String,BigDecimal> getMinMaxPriceProductByCategoryURL(String category) {
        Map<String, BigDecimal> minMaxPrice = new HashMap<>();
        List<BigDecimal[]> minMax = productRepo.minMaxPriceBySubCategory(category);
        for (BigDecimal[] m : minMax) {
            minMaxPrice.put("min", m[0]);
            minMaxPrice.put("max", m[1]);
        }
        return minMaxPrice;
    }

    @Override
    public Map<String,BigDecimal> getMinMaxPriceProductBySearchName(String search) {
        Map<String, BigDecimal> minMaxPrice = new HashMap<>();
        List<BigDecimal[]> minMax = productRepo.searchMinMaxPriceProductByNameLike(search);
        for (BigDecimal[] m : minMax) {
            minMaxPrice.put("min", m[0]);
            minMaxPrice.put("max", m[1]);
        }
        return minMaxPrice;
    }

    private void uploadImgProduct(MultipartFile file, Product product) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(env.getProperty("upload.path"));
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            System.out.println("resultFilename-------"+resultFilename);

            try {
                file.transferTo(new File(env.getProperty("upload.path") + "/" + resultFilename));
            } catch (IOException e) {
                System.out.println("error save file to disk");
                e.printStackTrace();
            }
            product.setImgLink(resultFilename);
        }
    }


    @Override
    public List<Producer> getProducersBySearchProduct(String search) {
        List<Product> products =  productRepo.findDistinctByNameContaining(search);
        List<Producer> producers = products.stream().map(Product::getProducer).filter(Objects::nonNull).distinct().collect(Collectors.toList());
        return producers;
    }


    @Override
    public Page<Product> getProductByFilter(FilterProduct filterProduct, int page, int limit) {
        Page<Product> productPage;

        if (filterProduct.getProducers() == null) {
            productPage = productRepo.findBySubcategory_IdAndPriceBetween(filterProduct.getIdCategory(), filterProduct.getPrice()[0], filterProduct.getPrice()[1], PageRequest.of(page - 1, limit));

        } else {

            productPage = productRepo.findBySubcategory_IdAndPriceBetweenAndProducer_IdIn(
                    filterProduct.getIdCategory(), filterProduct.getPrice()[0], filterProduct.getPrice()[1],
                    filterProduct.getProducers(), PageRequest.of(page - 1, limit));
        }

        return productPage;
    }




    @Override
    public List<Producer> getProducersByCategoryURL(String categoryName) {

        List<Producer> producers = new ArrayList<>();

        for (Product product : productRepo.findAllBySubcategory_Url(categoryName)) {
            producers.add(product.getProducer());
        }
        return producers.stream().distinct().collect(Collectors.toList());

    }


    @Override
    public Page<Product> findProductByCategoryIDWherePriceAndProducer(long categoryId, BigDecimal min, BigDecimal max, Set<Long> producerId, int page, int limit) {
        Page<Product> productPage = productRepo.findBySubcategory_IdAndPriceBetweenAndProducer_IdIn(categoryId, min, max, producerId, PageRequest.of(page-1,limit));

        return productPage;
    }

    @Override
    public Subcategory findSubcategoryByURL(String categoryURl) {
        Subcategory subcategory = subCategoryRepo.findByUrl(categoryURl);
        if (subcategory == null) {
            throw new ResourceNotFoundException("not found category because url failed");
        }

        return subcategory;
    }
}
