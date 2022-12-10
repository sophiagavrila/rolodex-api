package com.revature;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.service.UserService;
import com.revature.web.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final ObjectMapper om = new ObjectMapper();
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.findAll()).thenReturn(Collections.emptySet());
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddUser() throws Exception {
        User user = new User();
        user.setUsername("test-user");
        user.setPassword("test-password");

        when(userService.add(user)).thenReturn(user);

        String json = om.writeValueAsString(user);

        mockMvc.perform(
                        post("/users/add")
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    // TODO: Complete all controller tests
//    @Test
//    public void testFindUserById() throws Exception {
//        User user = new User();
//        user.setId(1);
//        user.setUsername("test-user");
//        user.setPassword("test-password");
//
//        when(userService.getById(1)).thenReturn(user);
//
//        mockMvc.perform(get("/users/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.username").value("test-user"))
//
//    }

}






