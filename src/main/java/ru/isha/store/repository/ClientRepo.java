package ru.isha.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isha.store.entity.Client;

public interface ClientRepo extends JpaRepository<Client, Long> {

    Client findByPhone(String phone);

}
