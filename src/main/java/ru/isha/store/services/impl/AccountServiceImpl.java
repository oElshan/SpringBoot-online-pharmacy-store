package ru.isha.store.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.isha.store.entity.Account;
import ru.isha.store.entity.Role;
import ru.isha.store.repository.AccountRepo;
import ru.isha.store.repository.RoleRepo;
import ru.isha.store.services.AccountService;


@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    private final AccountRepo accountRepo;
    private final RoleRepo roleRepo;

    public AccountServiceImpl(AccountRepo accountRepo, RoleRepo roleRepo) {
        this.accountRepo = accountRepo;
        this.roleRepo = roleRepo;
    }


    @Override
    public Account findAccountByLogin(String login) {
        return accountRepo.findByName(login);
    }

    @Transactional
    @Override
    public Account createAccount(Account account) {
        Role role =  roleRepo.findByName(account.getRole().getName());
        account.setRole(role);
        return accountRepo.save(account);
    }
}
