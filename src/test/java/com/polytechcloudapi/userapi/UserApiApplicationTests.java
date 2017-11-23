package com.polytechcloudapi.userapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polytechcloudapi.userapi.controller.UserController;
import com.polytechcloudapi.userapi.model.User;
import com.polytechcloudapi.userapi.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
public class UserApiApplicationTests {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

	private MockMvc mockMvc;
    private User user;
	@Before
	public void setUp() throws Exception {
        double[] position = new double[2];
        position[0] = 67.2;
        position[1] = 24.9;

        user = new User();
        user.setId("toto");
        user.setlastName("last");
        user.setFirstName("first");
        user.setBirthDay(new Date("05/05/2015"));
        user.setPosition(position);

		//MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}


    @Test
    public void test_get_all_empty() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    public void test_get_one_empty() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/toto"))
				.andExpect(status().isNotFound());
    }


    @Test
    public void test_get_one_ok() throws Exception {

        when(this.userRepository.findOne("toto")).thenReturn(user);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/toto"))
                .andExpect(status().isOk())
                .andExpect(content().string("{" +
                        "\"id\":\"toto\"," +
                        "\"firstName\":\"first\"," +
                        "\"lastName\":\"last\"," +
                        "\"birthDay\":\"05/05/2015\"," +
                        "\"position\":{\"lat\":67.2,\"lon\":24.9}" +
                        "}"));
        verify(userRepository).findOne(anyString());
    }


    @Test
    public void test_put_one_not_found() throws Exception {
        when(userRepository.findOne("toto")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/user/toto")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isNotFound());
        verify(userRepository).findOne(anyString());
    }

    /*
    @Test
    public void test_put_one_ok() throws Exception {
        when(userRepository.findOne("toto")).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.put("/user/toto").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("{" +
                        "\"id\":\"toto\"," +
                        "\"firstName\":\"first\"," +
                        "\"lastName\":\"last\"," +
                        "\"birthDay\":\"05/05/2015\"," +
                        "\"position\":{\"lat\":67.2,\"lon\":24.9}" +
                        "}"));
    }
    */

    @Test
    public void test_delete_all_ok() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/user"))
                .andExpect(status().isOk());
    }

    @Test
    public void test_delete_one_not_found() throws Exception {
        //when(userRepository.findOne("toto")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/user/toto"))
                .andExpect(status().is(400));
        //verify(userRepository).findOne(anyString());
    }
}
