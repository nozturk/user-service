package dev.nozturk.userservice.service;

import dev.nozturk.userservice.repository.entity.User;

public interface UserService {
    String login(User user);

    String refreshToken(String refreshToken);

    String adminLogin(User authenticationRequest);
}
