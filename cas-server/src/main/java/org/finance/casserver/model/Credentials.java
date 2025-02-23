package org.finance.casserver.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {

    @NotBlank(message = "Username cannot be empty")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    // Optional additional fields for multi-factor authentication
    private String otpCode;

    // Optional field for remember-me functionality
    private boolean rememberMe;

    // For tracking authentication attempts
    private String clientIp;
    private String userAgent;

    // Constructor for basic authentication
    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Method to clear sensitive data
    public void clearSensitiveData() {
        this.password = null;
        this.otpCode = null;
    }

    // Validate credentials format (basic validation)
    public boolean isValid() {
        return username != null && !username.trim().isEmpty()
                && password != null && !password.trim().isEmpty();
    }
}