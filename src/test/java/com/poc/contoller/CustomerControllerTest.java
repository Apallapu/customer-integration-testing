package com.poc.contoller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.controller.CustomerController;
import com.poc.model.Customer;
import com.poc.service.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	CustomerService customerService;

	@Test
	public void createCustomerAPI() throws Exception {

		when(customerService.createCustomer(Mockito.any())).thenReturn(new Customer("firstName4", "lastName4"));
		mvc.perform(MockMvcRequestBuilders.post("/customer/create")
				.content(asJsonString(new Customer("firstName4", "lastName4"))).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists());
	}

	@Test
	public void updateCustomer() throws Exception {

		when(customerService.updateCustomer(Mockito.any(), Mockito.anyLong()))
				.thenReturn(new Customer("firstName4", "lastName4"));
		mvc.perform(MockMvcRequestBuilders.put("/customer/update/{id}", 1)
				.content(asJsonString(new Customer("firstName4", "lastName4"))).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].address.name").value("lastName4"));

	}

	@Test
	public void deleteCustomer() throws Exception {

		Mockito.doNothing().when(customerService).deleteCustomer(Mockito.anyLong());
		mvc.perform(MockMvcRequestBuilders.delete("/customer/delete/{id}", 1)).andExpect(status().isOk());

	}

	@Test
	public void getCustomerByName() throws Exception {

		when(customerService.getCustomer(Mockito.anyString())).thenReturn(new Customer("firstName4", "lastName4"));
		mvc.perform(MockMvcRequestBuilders.get("/customer?name=ankamma").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("firstName4"));
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
