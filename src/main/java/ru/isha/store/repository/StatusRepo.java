package ru.isha.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isha.store.entity.Status;

public interface StatusRepo extends JpaRepository<Status, Integer> {

    Status getById(Integer id);

    Status findByName(String name);
}
