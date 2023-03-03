package Tuhoc.example.demo;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SecurityInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(HttpServletRequest request, javax.servlet.http.HttpServletResponse response,
			Object handler) throws Exception {
		System.out.println("!!!!!!");
		System.out.println(request.getServletPath());
		System.out.println(request.getMethod());
		String path = request.getServletPath();
		
		// Thực tế nên tạo 1 entity AUTHORITY (path, role) tương ứng đường dẫn
		if (path.equals("/api/user/search")) { // THƯ VIỆN JAVA REGEX chuyên để so sánh String
			// role admin
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
				List<String> roles = auth.getAuthorities().stream().map(p -> p.getAuthority()).
						collect(Collectors.toList());
				if(!roles.contains("ROLE_ADMIN")) {
					throw new AccessDeniedException("");
				}
//				return true; 
			}
			throw new AccessDeniedException("");
		}
		return true;
	}
}
