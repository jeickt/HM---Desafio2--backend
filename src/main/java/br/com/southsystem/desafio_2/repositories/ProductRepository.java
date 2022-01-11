package br.com.southsystem.desafio_2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.southsystem.desafio_2.model.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

}
