package org.finance.casclient.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.finance.casclient.config.CasProperties;
import org.finance.casclient.model.CasUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// CasClientUtils.java
@Component
public class CasClientUtils {

    private final CasProperties casProperties;

    public String constructServiceUrl(HttpServletRequest request) {
        return casProperties.getClient().getServiceUrl() +
                casProperties.getClient().getCallback();
    }

    public String extractTicket(HttpServletRequest request) {
        return request.getParameter("ticket");
    }

    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken);
    }

    public CasUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CasUser) {
            return (CasUser) auth.getPrincipal();
        }
        return null;
    }
}