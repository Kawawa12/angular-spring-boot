package com.Shop;

import com.Shop.entity.Manager;
import com.Shop.entity.AppUser;
import com.Shop.entity.Role;
import com.Shop.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.ByteBuffer;

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
		AppUser existingManager = userRepository.findByRole(Role.MANAGER);
		if (existingManager == null) {
			Manager manager = new Manager();
			manager.setFullName("John Jackson");
			manager.setEmail("john123@gmail.com");
			manager.setPassword(passwordEncoder.encode("manager123"));
			manager.setPhone("0766-234-078");
			manager.setGender("Male");
			manager.setActive(true);
			manager.setRole(Role.MANAGER);
			manager.setAddress("Nyegez Mwanza");
			manager.setImg(null); // Set img to null
			userRepository.save(manager);
			System.out.println("Manager created successfully!");
		} else {
			System.out.println("Manager already exists.");
		}
	}
}