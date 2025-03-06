package io.notecalendar.Interceptor;

import io.notecalendar.Utils.JwtUtil;
import io.notecalendar.Utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {
//    identify user from token
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        try {
//            check if token exist and start with bearer
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); //get token without bearer
            }

            Map<String, Object> claims = JwtUtil.parseToken(token);


            ThreadLocalUtil.set(claims);
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK); // return HTTP 200
            }
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
