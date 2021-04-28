package fr.soro.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.soro.dto.CreateReservationDto;
import fr.soro.dto.OuvrageDto;
import fr.soro.entities.*;
import fr.soro.repositories.OuvrageRepository;
import fr.soro.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class OuvrageControllerITTest {
    @Autowired
    private OuvrageRepository ouvrageRepository;

    @Autowired
    private MockMvcBuilder mockMvcBuilder;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = mockMvcBuilder.build();

    }

    @Test
    public void shoulReturnOuvragesByCategory() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/category/{categorie}","roman")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        perform.andExpect(MockMvcResultMatchers.status().isFound());
        Assertions.assertThat(new ObjectMapper().readValue(perform.andReturn().getResponse().getContentAsString(), List.class)).hasSize(4);
    }

    @Test
    public void shoulReturnAllOuvrage() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/ouvrages")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(new ObjectMapper().readValue(perform.andReturn().getResponse().getContentAsString(), List.class)).hasSize(13);
    }

    @Test
    public void shouldGetOuvrageById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ouvrages-id/{id}",1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titre").value("Candide"));
               }

    @Test
    public void shouldDeleteOuvrage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/ouvrages/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isGone());
    }

    @Test
    public void shouldCreateReservationOuvrage() throws Exception {
        OuvrageDto ouvrage1 = new OuvrageDto();
        ouvrage1.setAuteur("Auteur1");
        ouvrage1.setTitre("Titre1");
        ouvrage1.setCategorie("Roman");
        ouvrage1.setNbreExemplaireDispo(1);
        mockMvc.perform(MockMvcRequestBuilders.post("/ouvrages")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtils.convertObjectToJsonBytes(ouvrage1)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.titre").value("Titre1"));
    }




}
