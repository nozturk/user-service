package dev.nozturk.userservice.service.impl;

import dev.nozturk.userservice.auth.CustomUserDetails;
import dev.nozturk.userservice.auth.JwtTokenProvider;
import dev.nozturk.userservice.repository.entity.User;
import dev.nozturk.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public String login(User user) {
        Authentication authentication = authenticate(user);

        return tokenProvider.generateToken(authentication);
    }

    @Override
    public String refreshToken(String refreshToken) {
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(tokenProvider.extractUsername(refreshToken));
        if (Objects.isNull(userDetails) || !tokenProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("Invalid token");
        }

        return tokenProvider.generateToken(userDetails.getId().toString());
    }

    @Override
    public String adminLogin(User user) {
        authenticate(user);
        return "Successfully logged in";
    }

    private Authentication authenticate(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
