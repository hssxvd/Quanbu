package com.qst.crop.security.service;

import com.qst.crop.entity.User;
import com.qst.crop.security.entity.JwtUser;
import com.qst.crop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s.这个用户不存在", username));
        }

        String role = user.getRole();
        if (role == null || role.isEmpty()) {
            role = "user";
        }
        String authority = "ROLE_" + role;
        return new JwtUser(user.getUserName(), user.getNickName(), user.getPassword(), user.getAvatar(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
    }
}
