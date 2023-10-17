package dev.nozturk.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/public")
public class DummyUserController {
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/greeting")
    public ResponseEntity<String> hello(@RequestParam String name){
        return ResponseEntity.ok("Welcome " + name);
    }
}

