package ru.isha.store.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.isha.store.entity.Role;

public interface RoleRepo extends PagingAndSortingRepository<Role, Integer> {


    Role findByName(String name);

}
