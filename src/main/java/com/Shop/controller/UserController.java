package com.Shop.controller;

import com.Shop.dto.CustomerOrderDto;
import com.Shop.dto.SignIn;
import com.Shop.dto.SignUp;
import com.Shop.response.Response;
import com.Shop.serveices.AuthService;
import com.Shop.serveices.CustomerOrderService;
import com.Shop.serveices.CustomerOrderServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http//:localhost:4200")
@RequestMapping("/auth/api")
public class UserController {

    private final AuthService authService;
    private final CustomerOrderService customerOrderService;

    @Autowired
    public UserController(AuthService authService, CustomerOrderService customerOrderService) {
        this.authService = authService;
        this.customerOrderService = customerOrderService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Response> signUp(@RequestBody SignUp request) {
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Response> signIn(@RequestBody SignIn request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @PostMapping("/place-order")
    public ResponseEntity<Response> placeOrder(@RequestBody CustomerOrderDto customerOrderDto, @RequestParam Long id){
        return ResponseEntity.ok(customerOrderService.createOrder(customerOrderDto, id));
    }

}
