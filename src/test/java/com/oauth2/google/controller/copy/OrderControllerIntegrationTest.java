package com.oauth2.google.controller.copy;

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
import com.oauth2.google.model.order.Order;
import com.oauth2.google.model.product.Product;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTest {
	
	 @LocalServerPort
	 private Integer port;
	 
	 private String baseUrl = "http://localhost";
	 
	 private static RestTemplate restTemplate = null;
	 
	 @BeforeAll
	 static void init() {
		 restTemplate = new RestTemplate();
	 }
	 
	 @BeforeEach
	 void setUp(){
		 baseUrl = baseUrl.concat(":").concat(port.toString()).concat("/api/orders");
	 } 
	 
	 @Test
     public void findAllProductTest() {
	 	ResponseEntity<List<Order>> responseEntity = restTemplate.exchange(baseUrl.concat("/all"),
			    HttpMethod.GET,  null, new ParameterizedTypeReference<List<Order>>() {});
	 
	 	List<Order> orders = responseEntity.getBody();
	 	assertAll(
	 			() -> assertNotNull(orders),
	 			() -> assertTrue(orders.size() > 0));
     }
	 
	 @Test
	 void createAProductTest() {
		 Order order = DataForTest.buildOrder(1000);
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_JSON);
		 HttpEntity<Order> postRequest = new HttpEntity<>(order, headers);
		 ResponseEntity<Order> postResponse = restTemplate.postForEntity(baseUrl.concat("/create"), postRequest, Order.class);
		 Order newOrder = postResponse.getBody();
		 assertAll(
				 () -> assertEquals(postResponse.getStatusCode(), HttpStatus.CREATED),
				 () -> assertNotNull(newOrder),
				 () -> assertEquals(order.getTotalPrice(), newOrder.getTotalPrice()),
				 () -> assertNotNull(newOrder.getId())
		 );
	 }
	
	 @Test
	 void foundOrderTest(){
		 ResponseEntity<Order> getResponse = restTemplate.getForEntity(baseUrl.concat("/find/{id}"), Order.class, 1);
		 Order order = getResponse.getBody();
		 assertAll(
				 () -> assertEquals(getResponse.getStatusCode(), HttpStatus.OK),
				 () -> assertNotNull(order)
		 );
	 }	
	 
	 @Test
	 void orderNotFoundTest(){
		 ResponseEntity<Order> getResponse = restTemplate.getForEntity(baseUrl.concat("/find/{id}"), Order.class, 10);
		 assertAll(
				 () -> assertEquals(getResponse.getStatusCode(), HttpStatus.NOT_FOUND)
		 );
	 }
	 
	 @Test
	 void deleteAOrderTest() {
		restTemplate.delete(baseUrl.concat("/delete/2"));
		ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/delete/{id}", String.class, 2);
		assertAll(
			() -> assertNotNull(response),
			() -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
			() -> assertTrue(response.getBody().contains("Unable to find entry with id 2"))
		);
	 }
	 
	 @Test
	 void searchByNameOrDescriptionTest() {
		 ResponseEntity<Order> getResponse = restTemplate.getForEntity(baseUrl.concat("/find/{id}"), Order.class, 1);
		 Order order = getResponse.getBody();
		 ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(baseUrl.concat("/search-by-name-description?orderId=" + order.getId() + "searchTerm=JavaScript"),
				    HttpMethod.GET,  null, new ParameterizedTypeReference<List<Product>>() {});
		 
		 List<Product> orders = responseEntity.getBody();
		 assertAll(
		 			() -> assertNotNull(orders),
		 			() -> assertTrue(orders.size() > 0));
	 }
}
