package ru.isha.store.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {
    private String name;
    private String email;
    private Long id;
    private String password;
    private Role role;

    public Account() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role",foreignKey = @ForeignKey(name = "fk_account_role"))

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

     private Account(AccountBuilder accountBuilder) {
        this.name = accountBuilder.name;
        this.email = accountBuilder.email;
        this.password = accountBuilder.password;
        this.role = accountBuilder.role;
    }

    public static class AccountBuilder {
        private String name;
        private String email;
        private String password;
        private Role role;

        public AccountBuilder() {
        }


        public AccountBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AccountBuilder setEmail(String email) {
            this.email = email;
            return this;
        }


        public AccountBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public AccountBuilder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Account build() {
            return new Account(this);
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(name, account.name) &&
                Objects.equals(email, account.email) &&
                Objects.equals(id, account.id) &&
                Objects.equals(password, account.password) &&
                Objects.equals(role, account.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, id, password, role);
    }
}
