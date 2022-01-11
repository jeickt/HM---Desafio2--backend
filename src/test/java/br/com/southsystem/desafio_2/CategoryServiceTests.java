package br.com.southsystem.desafio_2;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.southsystem.desafio_2.model.entities.Category;
import br.com.southsystem.desafio_2.services.CategoryService;
import br.com.southsystem.desafio_2.services.exceptions.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CategoryServiceTests {
	
	@Autowired
	CategoryService service;

	@Test
	public void getAll() {
		// ação
		List<Category> categories = service.findAll();
		
		//verificação
		Assert.assertEquals(3, categories.size());
	}
	
	@Test
	public void findById() {
		// cenário
		Long id = 3L;
		
		//ação
		Category category = service.findById(id);
		
		//verificação
		Assert.assertEquals("Computers", category.getName());
	}
	
	@Test (expected = ResourceNotFoundException.class)
	public void findByIdError() {
		// cenário
		Long id = 7L;

		// ação
		service.findById(id);
	}
	
	@Test
	public void insert() {
		// cenário
		Category cat = new Category("Furniture");

		// ação
		Category category = service.insert(cat);

		// verificação
		Assert.assertEquals("Furniture", category.getName());
	}
	
	@Test
	public void update() {
		// cenário
		Long id = 2L;
		Category cat = new Category("Magazines");

		// ação
		Category category = service.update(id, cat);
		
		// verificação
		Assert.assertEquals("Magazines", category.getName());
	}
	
	@Test (expected = ResourceNotFoundException.class)
	public void updateError() {
		// cenário
		Long id = 6L;
		Category cat = new Category("Magazines");

		// ação
		service.update(id, cat);
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
		Long id = 7L;
		
		// ação
		service.delete(id);
		
		// verificação
		service.findById(id);			
	}

}