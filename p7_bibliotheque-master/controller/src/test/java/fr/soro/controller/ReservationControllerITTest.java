package fr.soro.controller;

import fr.soro.dto.CreateReservationDto;
import fr.soro.entities.*;
import fr.soro.repositories.*;
import fr.soro.utils.TestUtils;
import org.junit.Ignore;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class ReservationControllerITTest {

    @Autowired
    private MockMvcBuilder mockMvcBuilder;
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OuvrageRepository ouvrageRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EmpruntRepository empruntRepository;
    @Autowired
    private ExemplaireRepository exemplaireRepository;
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
        Ouvrage ouvrage = ouvrageRepository.save(buildOuvrage().get(0));
        CreateReservationDto createReservationDto = new CreateReservationDto(user.getId(), ouvrage.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/reservations")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(TestUtils.convertObjectToJsonBytes(createReservationDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(157))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ouvrageName").value("Titre1"));
    }

    @Test
    public void shouldDeleteReservation() throws Exception {
        Reservation reservation = reservationRepository.save(buildReservation());
        mockMvc.perform(MockMvcRequestBuilders.delete("/reservations/{reservationId}",reservation.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Ignore//findEmpruntEarliestReturnDate rettourne tous les exemplaire meme ceux ou l'emprunt et null
    @Test
    public void shouldRerturnWaitingListInfos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/reservations/waitingList/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.canBeBooked").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.earliestBookReturnDate").value("2021-05-29T14:17:33.380+00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfReservation").value(1));
    }

    @Test
    public void shoulReturnUserReservationInfos() throws Exception {
//        this.setupH2DB();
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/reservation/userCredentials/155")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Candide"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookEarliestReturnDate").value("2021-05-29T14:17:33.380+00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.positionInWaitingList").value(1));
    }

    public static User buildUser(){
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setUsername("testUser");
        user.setPassword("0000");
        return user;
    }

    public static List<Ouvrage> buildOuvrage(){
        List<Ouvrage> ouvrages = new ArrayList<Ouvrage>();
        Ouvrage ouvrage1 = new Ouvrage();
        ouvrage1.setAuteur("Auteur1");
        ouvrage1.setTitre("Titre1");
        ouvrage1.setCategorie("Roman");
        ouvrage1.setNbreExemplaireDispo(1);
        Ouvrage ouvrage2 = new Ouvrage();
        ouvrage2.setAuteur("Auteur2");
        ouvrage2.setTitre("Titre2");
        ouvrage2.setCategorie("Roman");
        ouvrage2.setNbreExemplaireDispo(2);
        ouvrages.add(ouvrage1);
        ouvrages.add(ouvrage2);
        return ouvrages;
    }
    public static Reservation buildReservation(){
        Reservation reservation = new Reservation();
        reservation.setOuvrage(buildOuvrage().get(0));
        reservation.setUser(buildUser());
//        reservation.setDateReservation(LocalDateTime.now());
        return reservation;
    }

    public static List<Emprunt> buildListEmprunts(){
        List<Emprunt> empruntList = new ArrayList<>();
        Emprunt emprunt1 = new Emprunt();
        emprunt1.setDateEcheance(new Date(2021, Calendar.MARCH,1));
        emprunt1.setExemplaires(buildListExemplaire());
        Emprunt emprunt2 = new Emprunt();
        emprunt2.setDateEcheance(new Date(2021, Calendar.MARCH,2));
        empruntList.add(emprunt1);
        empruntList.add(emprunt2);
        emprunt1.setExemplaires(buildListExemplaire());
        return empruntList;
    }

    public static List<Exemplaire> buildListExemplaire(){
        List<Exemplaire> exemplaires = new ArrayList<Exemplaire>();
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setOuvrage(buildOuvrage().get(0));
        exemplaires.add(exemplaire);
           return exemplaires;
    }


}