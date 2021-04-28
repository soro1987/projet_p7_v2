package fr.soro.controller;

import fr.soro.dto.UserDto;
import fr.soro.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class UserControllerITTest {

    @Autowired
    private MockMvcBuilder mockMvcBuilder;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = mockMvcBuilder.build();

    }

    @Test
    public void shouldGetOuvrageById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/{username}","soro1987")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.prenom").value("soro"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("testmailocr2305@gmail.com"));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        UserDto user = new UserDto();
        user.setEmail("test@gmail.com");
        user.setUsername("testUser");
        user.setPassword("0000");
        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtils.convertObjectToJsonBytes(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;
    }

}
