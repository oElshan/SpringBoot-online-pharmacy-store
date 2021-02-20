package ru.isha.store.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.isha.store.entity.Account;

import java.util.Arrays;

public class CurrentUser extends User {

    private String email;
    private Long id;
    private  String role;


    public CurrentUser(Account account) {
        super(account.getName(), account.getPassword(), Arrays.asList(new SimpleGrantedAuthority(account.getRole().getName())));
        this.email = account.getEmail();
        this.id = account.getId();
        this.role = account.getRole().getName();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", role='" + role + '\'' +
                '}';
    }
}
