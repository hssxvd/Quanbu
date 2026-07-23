package com.qst.crop.security.entity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class RoleAuthenticationDetails extends WebAuthenticationDetails {
    private String selectedRole;

    public RoleAuthenticationDetails(HttpServletRequest request) {
        super(request);
    }

    public RoleAuthenticationDetails(HttpServletRequest request, String selectedRole) {
        super(request);
        this.selectedRole = selectedRole;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }
}