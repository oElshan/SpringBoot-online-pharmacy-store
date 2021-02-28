package ru.isha.store.filters;

import javax.servlet.*;
import java.io.IOException;

public class CustomURIFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
