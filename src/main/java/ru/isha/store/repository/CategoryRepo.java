package ru.isha.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isha.store.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    List<Category> findAll();

    Category findById(int id);


    Optional<Category> findByUrl(String url);

}
