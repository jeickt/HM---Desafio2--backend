package br.com.southsystem.desafio_2;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.southsystem.desafio_2.model.entities.Product;
import br.com.southsystem.desafio_2.services.ProductService;
import br.com.southsystem.desafio_2.services.exceptions.NegativeValueException;
import br.com.southsystem.desafio_2.services.exceptions.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductServiceTests {

	@Autowired
	ProductService service;

	@Test
	public void getAll() {
		// ação
		List<Product> products = service.findAll();

		// verificação
		System.out.println(products);
		Assert.assertEquals(3, products.size());
	}
	
	@Test
	public void findById() {
		// cenário
		Long id = 3L;

		// ação
		service.findById(id);
	}

	@Test (expected = ResourceNotFoundException.class)
	public void findByIdError() {
		// cenário
		Long id = 7L;

		// ação
		service.findById(id);
	}
	
	@Test
	public void findByCategoryId() {
		// cenário
		Long id = 3L;

		// ação
		List<Product> products = service.findByCategoryId(id);

		// verificação
		Assert.assertEquals(1, products.size());
	}

	@Test
	public void insert() {
		// cenário
		Product prod = new Product("Cafeteira", 453.00, 3);

		// ação
		Product product = service.insert(prod);

		// verificação
		Assert.assertEquals(453.00, product.getPrice(), 0.00);
	}
	
	@Test (expected = NegativeValueException.class)
	public void insertError() {
		// cenário
		Product prod = new Product("Cafeteira", 453.00, -3);

		// ação
		service.insert(prod);
	}
	
	@Test
	public void update() {
		// cenário
		Long id = 3L;
		Product prod = new Product("Java", 364.00, 5);

		// ação
		Product product = service.update(id, prod);
		
		// verificação
		Assert.assertEquals(364.00, product.getPrice(), 0.00);
		Assert.assertEquals(5, (int) product.getQuantity());
	}
	
	@Test (expected = ResourceNotFoundException.class)
	public void updateError() {
		// cenário
		Long id = 9L;
		Product prod = new Product("Java", 364.00, 5);

		// ação
		service.update(id, prod);
	}
	
	@Test
	public void delete() {
		// cenário
		Long id = 3L;
		
		// ação
		service.delete(id);		
	}
	
	@Test (expected = ResourceNotFoundException.class)
	public void deleteError() {
		// cenário
		Long id = 3L;
		
		// ação
		service.delete(id);
		
		// verificação
		service.findById(id);			
	}

}
