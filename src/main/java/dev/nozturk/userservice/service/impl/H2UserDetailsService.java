package dev.nozturk.userservice.service.impl;

import dev.nozturk.userservice.auth.CustomUserDetails;
import dev.nozturk.userservice.repository.UserRepository;
import dev.nozturk.userservice.repository.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class H2UserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow(()->new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user);
    }
}
