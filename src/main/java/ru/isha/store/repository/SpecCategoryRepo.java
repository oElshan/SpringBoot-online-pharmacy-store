package ru.isha.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isha.store.entity.SpecCategory;

import java.util.List;

public interface SpecCategoryRepo extends JpaRepository<SpecCategory, Integer> {

    List<SpecCategory> findAll();

    SpecCategory findByName(String specCategory);

}
