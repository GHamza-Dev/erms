package com.hahn.erms.security.service;

import com.hahn.erms.security.dao.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AppAuthorizationService {
    private final CustomUserDetailsService userDetailsService;

    public String getCurrentUserName() {
        log.info("Getting current authenticated user name");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Current authenticated user name: {}", username);
        return username;
    }

    public User getCurrentUser() {
        log.info("Getting current authenticated user");
        String username = getCurrentUserName();
        return userDetailsService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public boolean hasAnyRole(String... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (String role : roles) {
            if (authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role))) {
                return true;
            }
        }
        return false;
    }
}
