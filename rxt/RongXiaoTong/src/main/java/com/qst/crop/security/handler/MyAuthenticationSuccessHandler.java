package com.qst.crop.security.handler;

import com.alibaba.fastjson.JSON;
import com.qst.crop.common.Result;
import com.qst.crop.common.StatusCode;
import com.qst.crop.security.util.JwtTokenUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        System.out.println("生成的token: " + token);
        renderToken(httpServletResponse, token);
        System.out.println("登陆成功");
    }

    public void renderToken(HttpServletResponse response, String token) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        
        Result<String> result = new Result<>(true, StatusCode.OK, "登陆成功", token);
        String str = JSON.toJSONString(result);
        System.out.println("要返回的响应: " + str);
        
        try {
            response.getWriter().write(str);
            response.getWriter().flush();
            System.out.println("响应写入成功");
        } catch (Exception e) {
            System.out.println("写入响应失败，尝试使用OutputStream: " + e.getMessage());
            response.getOutputStream().write(str.getBytes("UTF-8"));
            response.getOutputStream().flush();
            System.out.println("使用OutputStream写入成功");
        }
    }
}