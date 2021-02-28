package ru.isha.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.isha.store.filters.CustomURIFilter;
import ru.isha.store.interceptors.ShoppingCartInterceptor;
import ru.isha.store.services.WebService;


@Configuration
public class WebConfig  implements WebMvcConfigurer {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    WebService webService;

    @Bean
    public FilterRegistrationBean<CustomURIFilter> someFilterRegistration() {
        FilterRegistrationBean<CustomURIFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(getCustomURIFilter());
        registration.setName("customURIFilter");
        return registration;
    }

    @Bean

    public CustomURIFilter getCustomURIFilter() {
        return new CustomURIFilter();
    }



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/conf/messages.properties");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean
    public ShoppingCartInterceptor getShoppingCartInterceptor() {
        return new ShoppingCartInterceptor(webService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getShoppingCartInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/rest/**");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


}
