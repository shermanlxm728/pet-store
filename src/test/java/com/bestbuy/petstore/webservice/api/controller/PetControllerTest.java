package com.bestbuy.petstore.webservice.api.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bestbuy.petstore.webservice.model.Pet;
import com.bestbuy.petstore.webservice.service.IService;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PetControllerTest {
	
	@MockBean
	IService service;

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	Pet pet;
	List<Pet> pets;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Before
	public void setUp() {
	    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		pet = new Pet();
		pet.setName("Sean");
		pet.setPetId(101);
		pet.setPrice(1.0);
		pet.setPetType("dog");


		pets = new ArrayList<>();
		pets.add(pet);
	}

	@Test
	public void getPetById() throws Exception {
		Mockito.when(service.getPetById(anyInt())).thenReturn(pet);
    	MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET, "/pet/1");
		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Sean"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").value(1.0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.type").value("dog"));


    	verify(service, times(1)).getPetById(anyInt());

	}

	
	@Test
	public void getAllPets() throws Exception {
		Mockito.when(service.getAllPets()).thenReturn(pets);
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.GET, "/pets");
		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Sean"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("dog"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(1.0));

		verify(service, times(1)).getAllPets();

	}
	
	@Test
	public void createPet() throws Exception {
		Mockito.when(service.createPet(pet)).thenReturn(pet);
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.POST, "/pet")
				.content(objectMapper.writeValueAsString(pet))
				.contentType("application/json");
		mockMvc.perform(requestBuilder)
				.andExpect(status().is2xxSuccessful());

		verify(service, times(1)).createPet(ArgumentMatchers.any());
	}


	@Test
	public void updatePetById() throws Exception {
		Mockito.when(service.updatePetById(101, pet)).thenReturn(pet);
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.PUT, "/pet/101")
				.content(objectMapper.writeValueAsString(pet))
				.contentType("application/json");
		mockMvc.perform(requestBuilder)
				.andExpect(status().is2xxSuccessful());


		verify(service,times(1)).updatePetById(eq(101), any());
	}

	@Test
	public void deleteById() throws  Exception{
		MockHttpServletRequestBuilder requestBuilder = request(HttpMethod.DELETE, "/pet/101");
		mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful());
	}
}
