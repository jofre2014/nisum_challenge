package com.nisum.challenge.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(String subject);

    boolean isTokenValid(String token, UserDetails userDetails);
}
