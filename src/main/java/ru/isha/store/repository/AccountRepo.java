package ru.isha.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.isha.store.entity.Account;

import java.util.List;

public interface AccountRepo extends JpaRepository<Account, Long> {

    Account findByName(String name);

    List<Account> findAllByRole(String role);

    List<Account> findAll();

    Account findByEmail(String email);

 }
