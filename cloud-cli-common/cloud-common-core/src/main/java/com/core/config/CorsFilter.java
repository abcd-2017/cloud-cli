package com.core.config;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lk
 * @date 2022-12-10
 * 解决跨域
 */
@Component
@Order(-9999)
public class CorsFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String origin = request.getHeader(HttpHeaders.ORIGIN);

        if (StringUtils.hasText(origin)) {
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Origin, x-requested-with, Content-Type, Accept, Authorization");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Cache-Control, Content-Language, Content-Type, Expires, Last-Modified, Pragma");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "60");
        }

        super.doFilter(request, response, chain);
    }
}
