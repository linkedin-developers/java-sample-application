/*
 * Test cases for Java Sample Application.
 */

package com.example.servingwebcontent;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = MainController.class)
public class ServingWebContentApplicationTest {

	@Autowired
	private MockMvc mockMvc;
    /***
	
	public void template() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk());
				//.andExpect(content().contentType(contentType)));
				//content().contentType(contentType)
				//.string(containsString("Java demo Authenticate"))
	} */

	@Test
	public void homePage() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(content().string(containsString("Please Click login with linkedIn Button to generate access token!")));
	}

}
