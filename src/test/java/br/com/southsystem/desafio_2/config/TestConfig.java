package br.com.southsystem.desafio_2.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.southsystem.desafio_2.model.entities.Category;
import br.com.southsystem.desafio_2.model.entities.Product;
import br.com.southsystem.desafio_2.model.entities.Role;
import br.com.southsystem.desafio_2.model.entities.User;
import br.com.southsystem.desafio_2.repositories.CategoryRepository;
import br.com.southsystem.desafio_2.repositories.ProductRepository;
import br.com.southsystem.desafio_2.repositories.RoleRepository;
import br.com.southsystem.desafio_2.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		
		Category cat1 = new Category("Electronics");
		Category cat2 = new Category("Books");
		Category cat3 = new Category("Computers");	
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		
		Product prod1 = new Product("Smart TV", 2190.0, 5, cat1);
		Product prod2 = new Product("PC Gamer", 1200.0, 3, cat3);
		Product prod3 = new Product("Rails for Dummies", 100.99, 8, cat2);
		productRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();	
		User user1 = new User("User", "user", encoder.encode("123456"), "user@gmail.com");
		User user2 = new User("Admin", "admin", encoder.encode("654321"), "admin@gmail.com");
		userRepository.saveAll(Arrays.asList(user1, user2));
		
		Role role1 = new Role("ROLE_USER");
		Role role2 = new Role("ROLE_ADMIN");
		roleRepository.saveAll(Arrays.asList(role1, role2));
		
		user1.getRoles().add(role1);
		user2.getRoles().add(role2);
		userRepository.saveAll(Arrays.asList(user1, user2));
		
	}

}