package com.springapps.cinemacityclone.controller;


import com.springapps.cinemacityclone.dto.AuthRequestDTO;
import com.springapps.cinemacityclone.model.User;
import com.springapps.cinemacityclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(userService.register(authRequestDTO));
    }
}
