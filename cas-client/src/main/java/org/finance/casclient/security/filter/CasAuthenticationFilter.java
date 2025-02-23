package org.finance.casclient.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

// CasAuthenticationFilter.java
@Component
public class CasAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CasAuthenticationFilter(AuthenticationManager authManager,
                                   CasProperties casProperties) {
        super("/login/cas");
        setAuthenticationManager(authManager);
        setServiceProperties(serviceProperties());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        String ticket = request.getParameter("ticket");
        String service = serviceProperties().getService();

        if (ticket == null) {
            throw new AuthenticationServiceException("No CAS ticket found");
        }

        CasAuthenticationToken authRequest =
                new CasAuthenticationToken(service, ticket);

        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        SecurityContextHolder.getContext().setAuthentication(authResult);

        // Create session if needed
        HttpSession session = request.getSession(true);
        session.setAttribute(
                SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );
    }
}