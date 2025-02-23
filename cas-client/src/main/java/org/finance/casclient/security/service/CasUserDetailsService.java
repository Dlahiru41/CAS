package org.finance.casclient.security.service;

import org.finance.casclient.model.CasUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// CasUserDetailsService.java
@Service
public class CasUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken> {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) {
        AttributePrincipal principal = token.getAssertion().getPrincipal();
        String username = principal.getName();

        // Get user attributes from CAS response
        Map<String, Object> attributes = principal.getAttributes();

        // Load user from database or create if doesn't exist
        User user = userRepository.findByUsername(username)
                .orElseGet(() -> createUser(username, attributes));

        return new CasUser(
                user.getId(),
                username,
                "",  // No password needed for CAS
                true, // enabled
                true, // accountNonExpired
                true, // credentialsNonExpired
                true, // accountNonLocked
                getAuthorities(attributes)
        );
    }

    private Set<GrantedAuthority> getAuthorities(Map<String, Object> attributes) {
        List<String> roles = (List<String>) attributes.get("roles");
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }
}
