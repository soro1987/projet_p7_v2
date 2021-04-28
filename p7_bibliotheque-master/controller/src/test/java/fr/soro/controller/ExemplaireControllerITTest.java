package fr.soro.controller;

import fr.soro.dto.CreateReservationDto;
import fr.soro.entities.Emprunt;
import fr.soro.entities.Exemplaire;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.User;
import fr.soro.repositories.ExemplaireRepository;
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

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class ExemplaireControllerITTest {

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private MockMvcBuilder mockMvcBuilder;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = mockMvcBuilder.build();

    }

    @Test
    public void shouldCreateExemplaire() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/exemplaires/{idOuvrage}/{idBiblio}",1,1)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        perform.andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    public void shouldGetExemplairesCountByBibliotheque() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/ouvrages/exemplairecount/{id}",1)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        perform.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldDeleteExemplaire() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/exemplaires/{id}",1)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isGone());
    }


}
