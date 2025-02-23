package org.finance.casserver.repository;

import org.finance.casserver.model.Credentials;
import org.finance.casserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   Optional<User> findByUsername(String username);

    default boolean validateCredentials(Credentials credentials) {
        return findByUsername(credentials.getUsername())
                .map(user -> {
                    // In a real implementation, use a password encoder
                    return user.isEnabled() &&
                            user.getPassword().equals(credentials.getPassword());
                })
                .orElse(false);
    }
}