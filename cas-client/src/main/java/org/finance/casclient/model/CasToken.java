package org.finance.casclient.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

// CasToken.java
@Getter
@Setter
public class CasToken implements Serializable {
    private String ticket;
    private String service;
    private LocalDateTime creationTime;
    private LocalDateTime expirationTime;
}