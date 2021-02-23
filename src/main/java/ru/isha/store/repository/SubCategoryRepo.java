package ru.isha.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isha.store.entity.Subcategory;

import java.util.List;

public interface SubCategoryRepo extends JpaRepository<Subcategory, Long> {

    List<Subcategory> findAll();
    Subcategory findByName(String subcategory);
    Subcategory findByUrl(String subcategory);
}
