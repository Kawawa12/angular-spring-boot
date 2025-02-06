package com.Shop.serveices;


import com.Shop.dto.SignIn;
import com.Shop.dto.SignUp;
import com.Shop.entity.AppUser;
import com.Shop.entity.Role;
import com.Shop.repo.UserRepository;
import com.Shop.response.Response;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthServicesImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthServicesImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                            AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Response signUp(SignUp signUpReq) {
        Response response = new Response();

        try{
            AppUser user = new AppUser();
            user.setFullName(signUpReq.getFullName());
            user.setEmail(signUpReq.getEmail());
            user.setRole(Role.USER);
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(signUpReq.getPassword()));
            user.setPhone(signUpReq.getPhone());
            AppUser savedUser = userRepository.save(user);
            if(savedUser.getId() > 0) {
                response.setStatus(200);
                response.setMessage("Successfully.");
            }else {
                response.setStatus(403);
                response.setMessage("Something went wrong, Retry.");
            }
        }catch (Exception e){
            response.setStatus(500);
            response.setMessage("Error! : " + e.getMessage());
        }

        return response;
    }

    @Override
    public Response signIn(SignIn request) {

        Response response = new Response();

       try{
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
           AppUser user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                   () -> new UsernameNotFoundException("User is not found.")
           );

           String token = jwtService.generateToken(user);
           String refToken = jwtService.generateRefreshToken(new HashMap<>(), user);

           response.setStatus(200);
           response.setMessage("Successful.");
           response.setId(user.getId());
           response.setRole(user.getRole().name());
           response.setJwtToken(token);
           response.setRefToken(refToken);
       }catch (Exception e){
           response.setStatus(500);
           response.setMessage(e.getMessage());
       }

       return response;
    }


}
