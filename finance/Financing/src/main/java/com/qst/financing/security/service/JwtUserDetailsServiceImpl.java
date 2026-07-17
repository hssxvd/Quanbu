package com.qst.financing.security.service;

import com.qst.financing.entity.BankUser;
import com.qst.financing.security.entity.JwtUser;
import com.qst.financing.service.BankUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private BankUserService bankUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BankUser bankUser = bankUserService.selectByUserName(username);
        if (bankUser == null) {
            throw new UsernameNotFoundException(String.format("%s.这个用户不存在", username));
        }

        String role = bankUser.getRole();
        if (role == null || role.isEmpty()) {
            role = "user";
        }
        String authority = "ROLE_" + role;
        return new JwtUser(bankUser.getUserName(), bankUser.getRealName(), bankUser.getPassword(), null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authority));
    }
}