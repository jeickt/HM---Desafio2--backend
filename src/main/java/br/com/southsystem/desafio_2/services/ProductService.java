package br.com.southsystem.desafio_2.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.southsystem.desafio_2.model.entities.Category;
import br.com.southsystem.desafio_2.model.entities.Product;
import br.com.southsystem.desafio_2.repositories.ProductRepository;
import br.com.southsystem.desafio_2.services.exceptions.FileReaderException;
import br.com.southsystem.desafio_2.services.exceptions.NegativeValueException;
import br.com.southsystem.desafio_2.services.exceptions.NullValueException;
import br.com.southsystem.desafio_2.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private CategoryService categoryService;

	public List<Product> findAll() {
		return repo.findAll();
	}

	public Product findById(Long id) {
		Optional<Product> product = repo.findById(id);
		return product.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public List<Product> findByCategoryId(Long categoryId) {
		return repo.findByCategoryId(categoryId);
	}

	public Product insert(Product product) {
		if (null == product.getPrice() || null ==product.getQuantity()) {
			throw new NullValueException("Preço e quantidade precisam ser informados.");
		}
		if (product.getPrice() < 0.0 || product.getQuantity() < 0) {
			throw new NegativeValueException("Preço e quantidade devem conter valores positivos.");
		}
		return repo.save(product);
	}

	public void insertFile(byte[] csvFile) {
		Scanner sc = null;
		List<Product> products = new ArrayList<>();
		List<Category> categories = new ArrayList<>();
		try {
			File file = File.createTempFile("csvFile", ".tmp");
			FileOutputStream in = new FileOutputStream(file);
			in.write(csvFile);
			in.close();

			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if ("codigo".equals(line.split(",")[1].substring(0, 6))) {
					continue;
				}
				String[] content = getCleanArray(line);

				Product newProduct = new Product(content[0], Double.parseDouble(content[1].replace(",", ".")),
						Integer.parseInt(content[2]));

				products.add(newProduct);
				categories.add(new Category(content[3]));
			}

			for (Product product : products) {
				insert(product);
			}

			for (Category category : categories) {
				categoryService.insert(category);
			}

			for (int i = 0; i < products.size(); i++) {
				products.get(i).setCategory(categories.get(i));
				insert(products.get(i));
			}
		} catch (IllegalStateException | IOException e) {
			throw new FileReaderException();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

	public Product update(Long id, Product product) {
		if (product.getPrice() < 0.0 || product.getQuantity() < 0) {
			throw new NegativeValueException("Preço e quantidade devem conter valores positivos.");
		}
		Optional<Product> p = repo.findById(id);
		if (p.isEmpty()) {
			throw new ResourceNotFoundException(id);
		}
		Product prod = p.get();
		updateData(prod, product);
		return repo.save(prod);
	}

	private void updateData(Product prod, Product product) {
		prod.setName(product.getName());
		prod.setPrice(product.getPrice());
		prod.setQuantity(product.getQuantity());
		prod.setCategory(product.getCategory());
	}

	public void delete(Long id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private String[] getCleanArray(String line) {
		String[] parts = line.split("\"");
		List<String> strings = new ArrayList<>();
		for (int i = 0; i < parts.length; i++) {
			if (i % 2 == 0) {
				String[] cels = parts[i].split(",");
				for (String cel : cels) {
					if (!cel.isEmpty()) {
						strings.add(cel);
					}
				}
			} else {
				strings.add(parts[i]);
			}
		}
		double basePrice = Double.parseDouble(strings.get(6).replace(',', '.'));
		double tax = Double.parseDouble(strings.get(7).replace(',', '.'));
		DecimalFormat decimal = new DecimalFormat("0.00");
		String finalPrice = decimal.format(basePrice * (tax / 100 + 1) * 1.45);

		String[] content = new String[4];
		content[0] = strings.get(3);
		content[1] = finalPrice;
		content[2] = Integer.toString(1);
		content[3] = strings.get(5);
		return content;
	}

}
