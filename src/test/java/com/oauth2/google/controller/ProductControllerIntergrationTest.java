package com.oauth2.google.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.oauth2.google.DataForTest;
import com.oauth2.google.model.product.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntergrationTest {

	 @LocalServerPort
	 private Integer port;
	 
	 /*@Autowired
     private TestRestTemplate testRestTemplate;

     @Autowired
     private TokenProvider tokenProvider;*/
	 
	 private String baseUrl = "http://localhost";
	 
	 private static RestTemplate restTemplate = null;
	 
	 @BeforeAll
	 static void init() {
		 restTemplate = new RestTemplate();
	 }
	 
	 @BeforeEach
	 void setUp(){
		 baseUrl = baseUrl.concat(":").concat(port.toString()).concat("/api/products");
	 } 
	 
	 @Test
	 public void authenticationTest() {
		 
	 }
	
	 @Test
     public void findAllProductTest() {
	 	ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(baseUrl.concat("/all"),
			    HttpMethod.GET,  null, new ParameterizedTypeReference<List<Product>>() {});
	 
	 	List<Product> products = responseEntity.getBody();
	 	assertAll(
	 			() -> assertNotNull(products),
	 			() -> assertTrue(products.size() > 0));
     }
	 
	 @Test
	 void createAProductTest() {
		 Product product = DataForTest.buildProduct();
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 HttpEntity<Product> postRequest = new HttpEntity<>(product, headers);
		 ResponseEntity<Product> postResponse = restTemplate.postForEntity(baseUrl.concat("/create"), postRequest, Product.class);
		 Product newProduct = postResponse.getBody();
		 assertAll(
				 () -> assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED),
				 () -> assertNotNull(newProduct),
				 () -> assertEquals(product.getName(), newProduct.getName()),
				 () -> assertNotNull(newProduct.getId())
		 );
	 }
	 
	 @Test
	 void foundProductTest(){
		 ResponseEntity<Product> getResponse = restTemplate.getForEntity(baseUrl.concat("/find/{id}"), Product.class, 1);
		 Product product = getResponse.getBody();
		 assertAll(
				 () -> assertEquals(getResponse.getStatusCode(), HttpStatus.OK),
				 () -> assertNotNull(product),
				 () -> assertEquals("JavaScript - The Fun Parts", product.getName())
		 );
	 }	
	 
	 @Test
	 void productNotFoundTest(){
		 ResponseEntity<Product> getResponse = restTemplate.getForEntity(baseUrl.concat("/find/{id}"), Product.class, 10);
		 assertAll(
				 () -> assertEquals(getResponse.getStatusCode(), HttpStatus.NOT_FOUND)
		 );
	 }

	@Test
	void shouldUpdateAProduct() {
		Product person = DataForTest.buildProduct();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Product> postRequest = new HttpEntity<>(person, headers);
		ResponseEntity<Product> responseEntity = restTemplate.exchange(baseUrl.concat("/{id}"), HttpMethod.PUT,
				postRequest, Product.class, 1);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}
	 
	@Test
	void deleteAProductTest() {
		restTemplate.delete(baseUrl.concat("/delete/2"));
		ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/delete/{id}", String.class, 2);
		assertAll(
			() -> assertNotNull(response),
			() -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
			() -> assertTrue(response.getBody().contains("Unable to find entry with id 2"))
		);
	}	
}
