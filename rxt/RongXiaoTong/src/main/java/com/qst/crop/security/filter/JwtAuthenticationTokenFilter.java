package com.qst.crop.security.filter;

import com.qst.crop.security.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(jwtTokenUtil.getHeader());
        System.out.println("JwtAuthenticationTokenFilter - 请求路径: " + request.getRequestURI());
        System.out.println("JwtAuthenticationTokenFilter - Authorization头: " + token);
        
        if (StringUtils.hasText(token)) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7).trim();
                System.out.println("JwtAuthenticationTokenFilter - 去掉Bearer后的token: " + token);
                System.out.println("JwtAuthenticationTokenFilter - token长度: " + token.length());
                
                String username = jwtTokenUtil.getUsernameFromToken(token);
                System.out.println("JwtAuthenticationTokenFilter - 解析出的用户名: " + username);
                
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    System.out.println("JwtAuthenticationTokenFilter - 加载的用户: " + userDetails.getUsername());
                    
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("JwtAuthenticationTokenFilter - Token验证成功，已设置认证信息");
                    } else {
                        System.out.println("JwtAuthenticationTokenFilter - Token验证失败");
                    }
                } else {
                    System.out.println("JwtAuthenticationTokenFilter - 用户名为空或已有认证信息");
                }
            } else {
                System.out.println("JwtAuthenticationTokenFilter - Token格式不正确，缺少Bearer前缀");
            }
        } else {
            System.out.println("JwtAuthenticationTokenFilter - 未找到Authorization头");
        }
        chain.doFilter(request, response);
    }
}