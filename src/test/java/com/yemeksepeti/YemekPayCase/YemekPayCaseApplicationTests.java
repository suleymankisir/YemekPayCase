package com.yemeksepeti.YemekPayCase;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.yemeksepeti.YemekPayCase.Repo.ToDoRepo;
import com.yemeksepeti.YemekPayCase.Service.ToDoService;
import com.yemeksepeti.YemekPayCase.dto.TodoRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class YemekPayCaseApplicationTests {

	@Autowired
	public MockMvc mockMvc;

	@Autowired
	public ToDoRepo toDoRepo;

	@Autowired
	public ToDoService toDoService;

	ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	@Test
	@Transactional
	public void saveTodo() throws Exception {

		String todoRequest = objectMapper.writeValueAsString(new TodoRequest("deneme","deneme testidir",
				LocalDate.now(),true));
		MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/todo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(todoRequest))
				.andReturn().getResponse();
		Assertions.assertEquals(HttpStatus.CREATED.value(),response.getStatus());

	}

	@Test
	public void toDoGetAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(this.toDoRepo.findAll().size())));
	}

	@Test
	public void toDoGetById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/todo/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("Deneme"))
				.andExpect(jsonPath("$.description").value("Deneme testi"))
                .andExpect(jsonPath("$.dueDate").value("2023-03-01"))
				.andExpect(jsonPath("$.completed").value(true));
	}

	@Test
	@Transactional
	public void updateById() throws Exception {

		String toDoRequest = objectMapper.writeValueAsString(new TodoRequest("deneme","deneme testidir",
				LocalDate.parse("2023-04-01"),false));
		mockMvc.perform(MockMvcRequestBuilders.put("/api/todo/2")
						.content(toDoRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.title").value("deneme"))
				.andExpect(jsonPath("$.description").value("deneme testidir"))
				.andExpect(jsonPath("$.dueDate").value("2023-04-01"))
				.andExpect(jsonPath("$.completed").value(false));
	}
	@Test
	@Transactional
	public void deleteById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/todo/2"))
				.andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.title").value("Deneme"))
				.andExpect(jsonPath("$.description").value("Deneme testi"))
				.andExpect(jsonPath("$.dueDate").value("2023-03-01"))
				.andExpect(jsonPath("$.completed").value(true));
	}

}
