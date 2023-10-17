package dev.nozturk.userservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import dev.nozturk.userservice.repository.entity.User;
import dev.nozturk.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/auth")
public class LoginController {
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User authenticationRequest) {
        return ResponseEntity.ok(userService.login(authenticationRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody JsonNode refreshToken) {
        return ResponseEntity.ok(userService.refreshToken(refreshToken.findValuesAsText("refreshToken").get(0)));
    }
}
