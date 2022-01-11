package br.com.southsystem.desafio_2.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.southsystem.desafio_2.model.entities.Role;
import br.com.southsystem.desafio_2.model.entities.User;
import br.com.southsystem.desafio_2.repositories.RoleRepository;
import br.com.southsystem.desafio_2.repositories.UserRepository;

@Configuration
@Profile("prod")
public class ProdConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();	
		User user1 = new User("User", "user", encoder.encode("123456"), "user@gmail.com");
		User user2 = new User("Admin", "admin", encoder.encode("654321"), "admin@gmail.com");
		userRepository.saveAll(Arrays.asList(user1, user2));
		
		Role role1 = new Role("ROLE_USER");
		Role role2 = new Role("ROLE_ADMIN");
		roleRepository.saveAll(Arrays.asList(role1, role2));
		
		user1.getRoles().add(role1);
		user2.getRoles().addAll(Arrays.asList(role1, role2));
		userRepository.saveAll(Arrays.asList(user1, user2));
	}

}