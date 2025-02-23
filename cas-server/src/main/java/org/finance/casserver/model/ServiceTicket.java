package org.finance.casserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ServiceTicket {

    private final String id;
    private final Authentication authentication;
    private final LocalDateTime expiration;
    private final LocalDateTime creation = LocalDateTime.now();

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiration);
    }
}
