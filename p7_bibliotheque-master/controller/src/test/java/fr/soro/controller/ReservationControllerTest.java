package fr.soro.controller;

import fr.soro.dto.CreateReservationDto;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.User;
import fr.soro.repositories.OuvrageRepository;
import fr.soro.repositories.UserRepository;
import fr.soro.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class ReservationControllerItTest {

    @Autowired
    private MockMvcBuilder mockMvcBuilder;
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OuvrageRepository ouvrageRepository;
    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        mockMvc = mockMvcBuilder.build();

    }

    @Test
    public void shouldCreateReservation() throws Exception {
        Mockito.doReturn(ResponseEntity.ok().build()).when(restTemplate)
                .postForEntity(Mockito.anyString(),Mockito.isA(HttpEntity.class),Mockito.eq(String.class));
        User user = userRepository.save(buildUser());
        Ouvrage ouvrage = ouvrageRepository.save(buildOuvrage());
        CreateReservationDto createReservationDto = new CreateReservationDto(user.getId(), ouvrage.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtils.convertObjectToJsonBytes(createReservationDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ouvrageName").value("Titre"));
    }

    public static User buildUser(){
        User user = new User();
        user.setEmail("soroamet@gmail.com");
        user.setUsername("soro2305");
        user.setPassword("2305");
        return user;
    }
    public static Ouvrage buildOuvrage(){
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setAuteur("Moliere");
        ouvrage.setTitre("Titre");
        ouvrage.setCategorie("Roman");
        ouvrage.setNbreExemplaireDispo(5);
        return ouvrage;
    }


}