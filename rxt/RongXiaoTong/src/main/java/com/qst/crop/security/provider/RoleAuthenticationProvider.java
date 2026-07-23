package com.qst.crop.security.provider;

import com.qst.crop.entity.User;
import com.qst.crop.security.entity.JwtUser;
import com.qst.crop.security.entity.RoleAuthenticationDetails;
import com.qst.crop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RoleAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        // 获取用户选择的角色
        String selectedRole = null;
        if (authentication.getDetails() instanceof RoleAuthenticationDetails) {
            selectedRole = ((RoleAuthenticationDetails) authentication.getDetails()).getSelectedRole();
        }

        // 查询用户
        User user = userService.selectByUserName(username);
        if (user == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        // 获取用户的实际角色
        String userRole = user.getRole();
        if (userRole == null || userRole.isEmpty()) {
            userRole = "user";
        }

        // 验证选择的角色是否合法（用户必须拥有该角色）
        String finalRole = userRole;
        if (selectedRole != null && !selectedRole.isEmpty()) {
            // 检查用户是否有权限选择该角色
            // 管理员可以选择任何角色
            // 专家只能选择 expert 或 user
            // 普通用户只能选择 user
            if ("admin".equals(userRole)) {
                // 管理员可以选择任何角色
                finalRole = selectedRole;
            } else if ("expert".equals(userRole)) {
                // 专家可以选择 expert 或 user
                if ("expert".equals(selectedRole) || "user".equals(selectedRole)) {
                    finalRole = selectedRole;
                } else {
                    throw new BadCredentialsException("您没有权限以该身份登录");
                }
            } else {
                // 普通用户只能选择 user
                if ("user".equals(selectedRole)) {
                    finalRole = selectedRole;
                } else {
                    throw new BadCredentialsException("您没有权限以该身份登录");
                }
            }
        }

        String authority = "ROLE_" + finalRole;
        JwtUser jwtUser = new JwtUser(user.getUserName(), user.getNickName(), user.getPassword(), 
                user.getAvatar(), AuthorityUtils.commaSeparatedStringToAuthorityList(authority));

        return new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}