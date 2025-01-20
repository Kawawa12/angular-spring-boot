package com.Shop;

import com.Shop.entity.Admin;
import com.Shop.entity.AppUser;
import com.Shop.entity.Role;
import com.Shop.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class ShopApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

    public ShopApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		AppUser existingAdmin = userRepository.findByRole(Role.ADMIN);
		if(existingAdmin == null) {
			Admin admin = new Admin();
			admin.setFullName("John Jackson");
			admin.setEmail("jon123@gmail.com");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setPhone("0766-234-078");
			admin.setGender("Female");
			admin.setRole(Role.ADMIN);
			admin.setAddress("Nyegez Mwanza");
			admin.setImageUrl(null);
			userRepository.save(admin);

		}

	}
}
