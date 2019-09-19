package com.nexio.magasin.test.integration.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.minidev.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApiTest {
	
	@LocalServerPort
    int port;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	 
	@Before
	public void initialiseRestAssuredMockMvcWebApplicationContext() {
	    RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	    RestAssured.port = port;
	}
 
	@Test
	public void whenGetCallOnProductURL_thenSuccesAndJSONResponseWithAPageOfElement() {
		
		given()
		.when()
		.get("/api/v1/products")
		.then()
		.statusCode(200)
		.assertThat().body("content[0].name", equalTo("Desktop"))
		.and()
		.assertThat().body("content[9].name", equalTo("Mouse unwired"))
		.and()
		.assertThat().body("pageable.pageSize", equalTo(10))
		.and()
		.assertThat().body("totalElements", equalTo(13))
		.and()
		.assertThat().body("numberOfElements", equalTo(10))
		.and()
		.assertThat().body("first", equalTo(true)); 
		
		given()
		.when()
		.get("/api/v1/products?page=1")
		.then()
		.statusCode(200)
		.assertThat().body("content[0].name", equalTo("Programming Java book"))
		.and()
		.assertThat().body("content[2].name", equalTo("Display 30"))
		.and()
		.assertThat().body("pageable.pageSize", equalTo(10))
		.and()
		.assertThat().body("totalElements", equalTo(13))
		.and()
		.assertThat().body("numberOfElements", equalTo(3))
		.and()
		.assertThat().body("first", equalTo(false));
	}  
	
	@Test
	public void whenGetCallOnSpecificProductURL_thenSuccesANdJSONResponseWithTheDataOfTheProduct() {
		
		given()
		.log().all()
		.when()
		.get("/api/v1/products/8")
		.then()
		.log().all()
		.statusCode(200)
		.and()
		.assertThat().body("id", equalTo(8))
		.and()
		.assertThat().body("name", equalTo("Tomato"))
		.and()
		.assertThat().body("code", equalTo("QWERRTYML"))
		.and()
		.assertThat().body("price", is(0.98f))
		.and()
		.assertThat().body("detail", equalTo("Tomatoes 1Kg")); 
	}
	
	@Test
	public void flowOfDifferentCallsToTheAPI() {
	
		/**
		 * When there is a POST request to /api/v1/product_items passing the id of a valid product as parameter and
		 * there is no cart
		 * then the status code is 201
		 * and a cart is created
		 * and the cart_id is sent in a cookie
		 * and the product is added to the cart
		 */
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", 9);
		
		Response response = given()
							.log().all()
							.contentType("application/json\r\n")
							.body(requestParams.toJSONString())
							.post("/api/v1/product_items")
							.andReturn();
										
		ValidatableResponse vResponse = response.then()
												.log().all()
												.statusCode(201);
		
		vResponse.assertThat().body("product_id", equalTo(9))
				 .and()
				 .assertThat().body("price", is(65.99f))
				 .and()
				 .assertThat().body("quantity", equalTo(1));
		
		Long cartId = Long.parseLong(response.getCookie("cart_id"));
		
		assertThat(cartId != null);

		/**
		 * When there is a POST request to /api/v1/product_items passing the id of a valid product as parameter and 
		 * the cart_id value as cookie
		 * then quantity of the product in the cart is increased by 1
		 */
		requestParams = new JSONObject();
		requestParams.put("id", 9);
		
		Cookie cookieCart = new Cookie.Builder("cart_id", cartId.toString()).build();
		
		given()
		.log().all()
		.contentType("application/json\r\n")
		.cookie(cookieCart)
		.body(requestParams.toJSONString())
		.post("/api/v1/product_items")
		.then()
		.log().all()
		.statusCode(201)
		.and()
		.assertThat().body("product_id", equalTo(9))
		.and()
		.assertThat().body("price", is(65.99f))
		.and()
		.assertThat().body("quantity", equalTo(2));
		
		/**
		 * When there is a POST request to /api/v1/product_items passing the id of a valid product as parameter and 
		 * the cart_id value as cookie
		 * then the product is added to the cart and the status code is 201
		 */
		requestParams = new JSONObject();
		requestParams.put("id", 7);
		
		given()
		.log().all()
		.contentType("application/json\r\n")
		.cookie(cookieCart)
		.body(requestParams.toJSONString())
		.post("/api/v1/product_items")
		.then()
		.log().all()
		.statusCode(201)
		.and()
		.assertThat().body("product_id", equalTo(7))
		.and()
		.assertThat().body("price", is(35.99f))
		.and()
		.assertThat().body("quantity", equalTo(1));
	 
		/**
		 * When there is a GET request to /api/v1/carts/{id} with a valid id
		 * then all products in the cart are returned and status code is 200
		 */
		given()
		.log().all()
		.contentType("application/json\r\n")
		.cookie(cookieCart)
		.get(String.format("/api/v1/carts/%s", cartId))
		.then()
		.log().all()
		.statusCode(200)
		.and()
		.assertThat().body("[0].product_id", equalTo(9))
		.and()
		.assertThat().body("[0].price", is(65.99f))
		.and()
		.assertThat().body("[0].quantity", equalTo(2))
		.and()
		.assertThat().body("[1].product_id", equalTo(7))
		.and()
		.assertThat().body("[1].price", is(35.99f))
		.and()
		.assertThat().body("[1].quantity", equalTo(1));
	
		/**
		 * When there is a DELETE request to /api/v1/product_items and a id of a product in the cart is passed by params
		 * then the status is 200 an the product quantity is decreased in the cart
		 */
		requestParams = new JSONObject();
		requestParams.put("id", 9);
		
		given()
		.log().all()
		.contentType("application/json\r\n")
		.cookie(cookieCart)
		.body(requestParams.toJSONString())
		.delete("/api/v1/product_items")
		.then()
		.log().all()
		.statusCode(200);
		
		given()
		.log().all()
		.contentType("application/json\r\n")
		.cookie(cookieCart)
		.get(String.format("/api/v1/carts/%s", cartId))
		.then()
		.log().all()
		.statusCode(200)
		.and()
		.assertThat().body("[0].product_id", equalTo(9))
		.and()
		.assertThat().body("[0].price", is(65.99f))
		.and()
		.assertThat().body("[0].quantity", equalTo(1))
		.and()
		.assertThat().body("[1].product_id", equalTo(7))
		.and()
		.assertThat().body("[1].price", is(35.99f))
		.and()
		.assertThat().body("[1].quantity", equalTo(1));

		/**
		 * When there is a POST request to /api/v1/login with valid credentials
		 * then the status code is 200 and is a cookie with a token in the response
		 */
		requestParams = new JSONObject();
		requestParams.put("email", "user@nexio.com");
		requestParams.put("password", "password");
		
		response = given()
					.log().all()
					.contentType("application/json\r\n")
					.cookie(cookieCart)
					.body(requestParams.toJSONString())
					.post("/api/v1/login")
					.andReturn();
		
		response
		.then()
		.log().all()
		.statusCode(200);
		
		String token = response.getCookie("token");
		
		assertThat(token != null);
		assertThat(token.length() > 0);
		
		/**
		 * When there is a DELETE request to /api/v1/logout without token then response code 400 is returned
		 */
		given()
		.log().all()
		.contentType("application/json\r\n")
		.delete("/api/v1/logout")
		.then()
		.log().all()
		.statusCode(400);
		
		/**
		 * When there is a DELETE request to /api/v1/logout with then right token then response code 200 is returned
		 */
		given()
		.log().all()
		.contentType("application/json\r\n")
		.cookie("token", token)
		.delete("/api/v1/logout")
		.then()
		.log().all()
		.statusCode(200);
		
		/**
		 * When there is a DELETE request to /api/v1/logout with a deleted token then response code 400 is returned
		 */
		given()
		.log().all()
		.contentType("application/json\r\n")
		.cookie("token", token)
		.delete("/api/v1/logout")
		.then()
		.log().all()
		.statusCode(400);
	}
}