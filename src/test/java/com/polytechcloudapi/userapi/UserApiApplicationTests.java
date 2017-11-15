package com.polytechcloudapi.userapi;

import com.polytechcloudapi.userapi.controller.UserController;
import com.polytechcloudapi.userapi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserApiApplicationTests {
	@Mock
	UserRepository userRepository;

	@InjectMocks
	UserController userController;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void get() throws Exception {
		when(userRepository.findAll()).thenReturn(Collections.emptyList());
		mockMvc.perform(MockMvcRequestBuilders.get("/user"))
				.andExpect(status().isOk())
				.andExpect(content().string("[]"));
	}

}
