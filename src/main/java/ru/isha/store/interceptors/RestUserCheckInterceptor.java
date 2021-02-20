package ru.isha.store.interceptors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import ru.isha.store.exception.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Перехватчик проверки авторизации для REST-службы.
 * <p>
 * При неавторизованном обращении к службе создаёт исключение AccessDeniedException,
 * которое возвращает клиенту соответствующий HTTP-статус.
 */
public class RestUserCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			throw new AccessDeniedException("");
		}
		return super.preHandle(request, response, handler);
	}
}
