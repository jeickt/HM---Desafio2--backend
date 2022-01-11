package br.com.southsystem.desafio_2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.southsystem.desafio_2.model.entities.Category;
import br.com.southsystem.desafio_2.repositories.CategoryRepository;
import br.com.southsystem.desafio_2.services.exceptions.DatabaseException;
import br.com.southsystem.desafio_2.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repo;
	
	public List<Category> findAll() {
		return repo.findAll();
	}
	
	public Category findById(Long id) {
		Optional<Category> category = repo.findById(id);
		return category.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Category insert(Category category) {
		return repo.save(category);
	}
	
	public Category update(Long id, Category category) {
		Optional<Category> c = repo.findById(id);
		if (c.isEmpty()) {
			throw new ResourceNotFoundException(id);
		}
		Category cat = c.get();
		updateData(cat, category);
		return repo.save(cat);
	}
	
	private void updateData(Category cat, Category category) {
		cat.setName(category.getName());
	}

	public void delete(Long id) {
		try {
			repo.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

}
