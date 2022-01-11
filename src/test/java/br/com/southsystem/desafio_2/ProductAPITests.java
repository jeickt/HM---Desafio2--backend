package br.com.southsystem.desafio_2;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.southsystem.desafio_2.model.entities.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Desafio2Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductAPITests extends BaseAPITest {
	
	@Autowired
	protected TestRestTemplate rest;
	
    private ResponseEntity<Product> getProduct(String url) {
        return get(url, Product.class);
    }

    private ResponseEntity<List<Product>> getProducts(String url) {
    	HttpHeaders headers = getHeaders();
        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<Product>>() {
                });
    }
    
    private ResponseEntity<List<Product>> getProductsByCategory(String url) {
    	HttpHeaders headers = getHeaders();
        return rest.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<Product>>() {
                });
    }
    
    @Test
    public void findAll() {
        List<Product> products = getProducts("/products").getBody();
        Assert.assertNotNull(products);
        Assert.assertEquals(3, products.size());
    }
    
	@Test
    public void findById() {
    	ResponseEntity<Product> response = getProduct("/products/1");
    	Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    	Product p = response.getBody();
    	Assert.assertEquals("Smart TV", p.getName());
    }
	
	@Test
	public void findByIdError() {
		ResponseEntity<Product> response = getProduct("/products/100");
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void findByCategoryId() {
		ResponseEntity<List<Product>> response = getProductsByCategory("/products/?categoryId=1");
    	Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void insertAndDelete() {
		Product product = new Product("Dom Quixote", 87.90, 4);
		ResponseEntity<Product> response  = post("/products", product, null);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		String location = response.getHeaders().get("location").get(0);
		Product p = getProduct(location).getBody();
		Assert.assertNotNull(p);
		Assert.assertEquals(87.90, p.getPrice(), 0.00);
		
		delete(location, null);
		Assert.assertEquals(HttpStatus.NOT_FOUND, getProduct(location).getStatusCode());
	}
	
//	@Test
//	public void update() {				
//	}

}
