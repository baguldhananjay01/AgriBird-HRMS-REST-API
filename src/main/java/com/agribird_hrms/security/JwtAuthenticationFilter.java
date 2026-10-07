package com.agribird_hrms.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(
            JwtService jwtService) {

        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        // ==========================================
        // NO TOKEN
        // ==========================================

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authHeader.substring(7);

        try {

            // ==========================================
            // VALIDATE TOKEN
            // ==========================================

            if (jwtService.isTokenValid(token)) {

                var claims =
                        jwtService.getClaims(token);

                String email =
                        claims.getSubject();

                String role =
                        claims.get("role", String.class);

                // ==========================================
                // ROLE AUTHORITY
                // ==========================================

                String authority =
                        "ROLE_EMPLOYEE";

                if (role != null
                        && role.equalsIgnoreCase(
                                "SUPER_ADMIN")) {

                    authority =
                            "ROLE_SUPER_ADMIN";
                }

                // ==========================================
                // AUTHENTICATION
                // ==========================================

                if (email != null
                        && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    Collections.singletonList(
                                            new org.springframework.security.core.authority.SimpleGrantedAuthority(
                                                    authority
                                            )
                                    )
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );
                }
            }

        } catch (Exception e) {

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}