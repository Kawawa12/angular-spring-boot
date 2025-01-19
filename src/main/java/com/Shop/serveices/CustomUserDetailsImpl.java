package com.Shop.serveices;


import com.Shop.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsImpl implements UserService{

    private final UserRepository userRepository;

    public CustomUserDetailsImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService UserDetailsService() {
        return username -> userRepository.findByEmail(username).orElseThrow(()-> new
                UsernameNotFoundException("User not found!"));
    }
}
