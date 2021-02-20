package ru.isha.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isha.store.entity.Producer;

import java.util.List;

public interface ProducerRepo extends JpaRepository<Producer, Long> {

    List<Producer> findAll();

    Producer findByName(String name);

}
