package ru.isha.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.isha.store.entity.Account;
import ru.isha.store.model.CurrentUser;
import ru.isha.store.repository.AccountRepo;

@Service
public class UserDetailServicesImpl implements UserDetailsService {

    @Autowired
    AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountRepo.findByName(username);

        if (account != null) {
            return new CurrentUser(account);
        }
        throw new UsernameNotFoundException("User not found.");
        }



}
