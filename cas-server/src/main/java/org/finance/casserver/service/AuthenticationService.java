package org.finance.casserver.service;

import org.finance.casserver.exception.AuthenticationException;
import org.finance.casserver.exception.TicketExpiredException;
import org.finance.casserver.exception.TicketNotFoundException;
import org.finance.casserver.model.Authentication;
import org.finance.casserver.model.Credentials;
import org.finance.casserver.model.ServiceTicket;
import org.finance.casserver.model.User;
import org.finance.casserver.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TicketRegistry ticketRegistry;
    private final UserRepository userRepository;

    public ServiceTicket authenticate(Credentials credentials) {
        if (!userRepository.validateCredentials(credentials)) {
            throw new AuthenticationException("Invalid credentials");
        }

        User user = userRepository.findByUsername(credentials.getUsername())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        Authentication auth = new Authentication(
                user,
                LocalDateTime.now(),
                credentials.getClientIp(),    // Added from Credentials
                credentials.getUserAgent()     // Added from Credentials
        );

        ServiceTicket ticket = new ServiceTicket(
                generateTicket(),
                auth,
                LocalDateTime.now().plusHours(1)  // 1 hour expiration
        );

        ticketRegistry.addTicket(ticket);
        return ticket;
    }

    public Authentication validateTicket(String ticketId) {
        ServiceTicket ticket = ticketRegistry.getTicket(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Invalid ticket: " + ticketId));

        if (ticket.isExpired()) {
            ticketRegistry.removeTicket(ticketId);
            throw new TicketExpiredException("Ticket has expired: " + ticketId);
        }

        return ticket.getAuthentication();
    }

    public void logout(String ticketId) {
        ticketRegistry.removeTicket(ticketId);
    }

    private String generateTicket() {
        return "ST-" + UUID.randomUUID().toString();
    }
}
