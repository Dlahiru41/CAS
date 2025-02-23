package org.finance.casserver.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Authentication {

    private final User user;
    private final LocalDateTime authenticatedAt;
    private final String clientIp;
    private final String userAgent;


}