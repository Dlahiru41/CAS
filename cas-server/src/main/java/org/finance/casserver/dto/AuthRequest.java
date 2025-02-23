package org.finance.casserver.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;


@Data
public class AuthRequest {
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    private String clientIp;    // Optional, can be set by controller
    private String userAgent;   // Optional, can be set by controller
}



