package ru.isha.store.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.isha.store.entity.Account;
import ru.isha.store.entity.Role;
import ru.isha.store.services.AccountService;
import ru.isha.store.services.ProductService;
import ru.isha.store.utils.Constants;

import javax.servlet.ServletContext;


@Component
public class ContextOnAppListener implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger log = LoggerFactory.getLogger(ContextOnAppListener.class);

    @Autowired
    private ProductService productService;
//
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private AccountService accountService;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent readyEvent) {

        Account account = accountService.findAccountByLogin("admin");

        if (account == null) {
            String passwordEncode = passwordEncoder.encode("admin");
            Role role = new Role();
            role.setName("ROLE_ADMIN");
             accountService.createAccount(new Account.AccountBuilder().setName("admin").setRole(role).setEmail("admin@admin.com").setPassword(passwordEncode).build());
            log.info("CREATE ADMIN ACCOUNT");
        }

        servletContext.setAttribute(Constants.CATEGORY_LIST,productService.findAllCategory());
        servletContext.setAttribute(Constants.SUBCATEGORY_LIST, productService.findAllSubCategory());
        servletContext.setAttribute(Constants.PRODUCER_LIST, productService.findAllProducer());
        servletContext.setAttribute(Constants.SPECCATEGORY_LIST, productService.findAllSpecCategory());

    }
}
