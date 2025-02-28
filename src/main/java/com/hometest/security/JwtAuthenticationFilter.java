package com.hometest.security;

import com.hometest.controllers.data.User;
import com.hometest.database.repository.StudentRepository;
import com.hometest.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  StudentRepository studentRepository;
    @Autowired
    private  UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String specialKeyHeader = request.getHeader("Special-Key");

        if (specialKeyHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            studentRepository.findBySpecialKey(specialKeyHeader).ifPresent(student -> {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(student, null, Collections.singletonList(new SimpleGrantedAuthority(student.getRole().name())));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            });
        }
        filterChain.doFilter(request, response);
    }
}
