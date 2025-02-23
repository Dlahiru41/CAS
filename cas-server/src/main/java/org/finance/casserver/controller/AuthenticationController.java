package org.finance.casserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.finance.casserver.dto.AuthRequest;
import org.finance.casserver.dto.AuthResponse;
import org.finance.casserver.dto.TicketValidation;
import org.finance.casserver.model.Authentication;
import org.finance.casserver.model.Credentials;
import org.finance.casserver.model.ServiceTicket;
import org.finance.casserver.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody AuthRequest request,
            HttpServletRequest httpRequest) {

        // Convert AuthRequest to Credentials
        Credentials credentials = new Credentials(
                request.getUsername(),
                request.getPassword()
        );

        // Set additional information from the HTTP request
        credentials.setClientIp(httpRequest.getRemoteAddr());
        credentials.setUserAgent(httpRequest.getHeader("User-Agent"));

        ServiceTicket ticket = authenticationService.authenticate(credentials);
        return ResponseEntity.ok(new AuthResponse(ticket.getId()));
    }

    @GetMapping("/validate/{ticketId}")
    public ResponseEntity<TicketValidation> validateTicket(@PathVariable String ticketId) {
        Authentication auth = authenticationService.validateTicket(ticketId);
        return ResponseEntity.ok(new TicketValidation(auth));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("X-Service-Ticket") String ticketId) {
        authenticationService.logout(ticketId);
        return ResponseEntity.ok().build();
    }
}