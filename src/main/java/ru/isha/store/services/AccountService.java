package ru.isha.store.services;

import ru.isha.store.entity.Account;

public interface AccountService {

    Account findAccountByLogin(String login);

    Account createAccount(Account account);
}
