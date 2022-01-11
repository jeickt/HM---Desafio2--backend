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

import br.com.southsystem.desafio_2.model.entities.Category;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Desafio2Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryAPITests extends BaseAPITest {
	
	@Autowired
	protected TestRestTemplate rest;
	
    private ResponseEntity<Category> getCategory(String url) {
        return get(url, Category.class);
    }

    private ResponseEntity<List<Category>> getCategories(String url) {
    	HttpHeaders headers = getHeaders();
        return rest.withBasicAuth("user", "123456").exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<Category>>() {
                });
    }
    
    @Test
    public void findAll() {
        List<Category> products = getCategories("/categories").getBody();
        Assert.assertNotNull(products);
        Assert.assertEquals(3, products.size());
    }
    
	@Test
    public void findById() {
    	ResponseEntity<Category> response = getCategory("/categories/1");
    	Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    	Category c = response.getBody();
    	Assert.assertEquals("Electronics", c.getName());
    }
	
	@Test
	public void findByIdError() {
		ResponseEntity<Category> response = getCategory("/products/100");
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	@Test
	public void insertAndDelete() {
		Category category = new Category("Adapters");
		ResponseEntity<Category> response  = post("/categories", category, null);
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		String location = response.getHeaders().get("location").get(0);
		Category c = getCategory(location).getBody();
		Assert.assertNotNull(c);
		Assert.assertEquals("Adapters", c.getName());
		
		delete(location, null);
		Assert.assertEquals(HttpStatus.NOT_FOUND, getCategory(location).getStatusCode());
	}
	
//	@Test
//	public void update() {				
//	}

}
