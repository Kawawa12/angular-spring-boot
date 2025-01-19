package com.Shop.controller;

import com.Shop.dto.SignIn;
import com.Shop.dto.SignUp;
import com.Shop.response.Response;
import com.Shop.serveices.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http//:localhost:4200")
@RequestMapping("/auth/api")
public class UserController {

    private final AuthService authService;

    @Autowired
    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Response> signUp(@RequestBody SignUp request) {
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Response> signIn(@RequestBody SignIn request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

}
