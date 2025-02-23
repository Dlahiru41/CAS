package org.finance.casserver.dto;

import lombok.Data;
import org.finance.casserver.model.Authentication;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TicketValidation {
    private final String username;
    private final Set<String> roles;
    private final LocalDateTime authenticatedAt;

    public TicketValidation(Authentication auth) {
        this.username = auth.getUser().getUsername();
        this.roles = auth.getUser().getRoles();
        this.authenticatedAt = auth.getAuthenticatedAt();
    }
}