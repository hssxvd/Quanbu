package com.qst.crop.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qst.crop.security.entity.AuthenticationBean;
import com.qst.crop.security.entity.RoleAuthenticationDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (isJsonRequest(request)) {
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest;
            try (InputStream is = request.getInputStream()) {
                AuthenticationBean authenticationBean = mapper.readValue(is, AuthenticationBean.class);
                String username = authenticationBean.getUsername();
                String password = authenticationBean.getPassword();
                String role = authenticationBean.getRole();
                authRequest = new UsernamePasswordAuthenticationToken(
                        StringUtils.hasText(username) ? username.trim() : username,
                        password != null ? password.trim() : null);
                // 将角色信息存入认证详情
                authRequest.setDetails(new RoleAuthenticationDetails(request, role));
            } catch (IOException e) {
                throw new AuthenticationServiceException("登录请求格式错误", e);
            }
            return this.getAuthenticationManager().authenticate(authRequest);
        }
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
        response.flushBuffer();
    }

    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().contains("application/json");
    }
}
