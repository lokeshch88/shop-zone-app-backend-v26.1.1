package com.shopzone.app.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shopzone.app.controller.ProductController;
import com.shopzone.app.service.UserService;
import com.shopzone.app.utils.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // ==========================================
        // GET JWT
        // ==========================================

        if (header != null && header.startsWith("Bearer ")) {

            token = header.substring(7);

            try {

                username = jwtUtil.extractUsername(token);

            } catch (Exception e) {

                response.setStatus(
                        HttpServletResponse.SC_UNAUTHORIZED
                );

                response.setContentType("application/json");
//                log.error("Error extracting username from JWT: {}", e.getMessage());

                response.getWriter().write(
                        "{\"error\":\"Invalid JWT token\"}"
                );

                return;
            }
        }

        // ==========================================
        // LOAD USER
        // ==========================================

        if (username != null
                && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails =
                    userService.loadUserByUsername(username);

            // ==========================================
            // VALIDATE TOKEN
            // ==========================================

            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        // ==========================================
        // CONTINUE REQUEST
        // ==========================================

        chain.doFilter(request, response);
    }
}