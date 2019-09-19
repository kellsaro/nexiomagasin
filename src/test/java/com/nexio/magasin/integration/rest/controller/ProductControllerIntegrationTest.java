package com.nexio.magasin.integration.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {
	
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
	public void givenProductsURL_whenSuccessOnGetsResponseAndJsonHasRequiredKV_thenCorrect() {
		
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
	}  
}