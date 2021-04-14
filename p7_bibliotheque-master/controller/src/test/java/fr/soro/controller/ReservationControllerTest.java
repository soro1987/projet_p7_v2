package fr.soro.controller;

import fr.soro.dto.CreateReservationDto;
import fr.soro.entities.*;
import fr.soro.repositories.*;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ouvrageName").value("Titre1"));
    }

    @Test
    public void shouldDeleteReservation() throws Exception {
        Reservation reservation = reservationRepository.save(buildReservation());
        mockMvc.perform(MockMvcRequestBuilders.delete("/reservations/{reservationId}",reservation.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldRerturnWaitingListInfos() throws Exception {
        this.setupH2DB();
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/reservations/waitingList/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.canBeBooked").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.earliestBookReturnDate").value("3921-02-28T23:00:00.000+00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfReservation").value(4));
    }

    @Test
    public void shoulReturnUserReservationInfos() throws Exception {
        this.setupH2DB();
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/reservation/userCredentials/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Titre1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookEarliestReturnDate").value("3921-02-28T23:00:00.000+00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.positionInWaitingList").value(3));
    }
    @Transactional
    public void setupH2DB(){
        //Create ou user to build our reservation
        User user = new User(); //Our user
        user.setEmail("test@gmail.com");
        user.setUsername("testUser");
        user.setPassword("0000");

        //Create Emprunts Users
        User user1 = new User();
        user1.setEmail("test@gmail1.com");
        user1.setUsername("testUser1");
        user1.setPassword("0000");
        User user2 = new User();
        user2.setEmail("test@gmail2.com");
        user2.setUsername("testUser2");
        user2.setPassword("0000");
        User user3 = new User();
        user3.setEmail("test@gmail3.com");
        user3.setUsername("testUser3");
        user3.setPassword("0000");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        //Create other Reservation Users
        User user4 = new User();
        user4.setEmail("test@gmail4.com");
        user4.setUsername("testUser4");
        user4.setPassword("0000");
        User user5 = new User();
        user5.setEmail("test@gmail5.com");
        user5.setUsername("testUser5");
        user5.setPassword("0000");
        User user6 = new User();
        user6.setEmail("test@gmail6.com");
        user6.setUsername("testUser6");
        user6.setPassword("0000");
        userRepository.save(user4);
        userRepository.save(user5);
        userRepository.save(user6);

        //Create and persist needed ouvrage
        Ouvrage ouvrage1 = new Ouvrage();
        ouvrage1.setAuteur("Auteur1");
        ouvrage1.setTitre("Titre1");
        ouvrage1.setCategorie("Roman");
        ouvrage1.setNbreExemplaireDispo(3);

        //Create and persist emprunts containing exemplaire of the particular ouvrage
        //To calcule earliest return date
        Emprunt emprunt1 = new Emprunt();
        emprunt1.setDateEcheance(new Date(2021, Calendar.MARCH,1));
        emprunt1.setUser(user1);
        Emprunt emprunt2 = new Emprunt();
        emprunt2.setDateEcheance(new Date(2021, Calendar.MARCH,2));
        emprunt2.setUser(user2);
        Emprunt emprunt3 = new Emprunt();
        emprunt3.setDateEcheance(new Date(2021, Calendar.MARCH,3));
        emprunt3.setUser(user3);
        empruntRepository.save(emprunt1);
        empruntRepository.save(emprunt2);
        empruntRepository.save(emprunt3);

        //Create Exemplaires of the particular ouvrage to build needed emprunt
        Exemplaire exemplaire1 = new Exemplaire();
        exemplaire1.setOuvrage(ouvrage1);
        exemplaire1.setEmprunt(emprunt1);
        Exemplaire exemplaire2 = new Exemplaire();
        exemplaire2.setOuvrage(ouvrage1);
        exemplaire2.setEmprunt(emprunt2);
        Exemplaire exemplaire3 = new Exemplaire();
        exemplaire3.setOuvrage(ouvrage1);
        exemplaire3.setEmprunt(emprunt3);
        exemplaireRepository.save(exemplaire1);
        exemplaireRepository.save(exemplaire2);
        exemplaireRepository.save(exemplaire3);

        //Create and persist Reservation of the particular ouvrage with their users
        //To calcule position on waiting list
        Reservation reservation = new Reservation();//Our reservation
        reservation.setOuvrage(ouvrage1);
        reservation.setUser(user);
        Reservation reservation1 = new Reservation();
        reservation1.setOuvrage(ouvrage1);
        reservation1.setUser(user4);
        Reservation reservation2 = new Reservation();
        reservation2.setOuvrage(ouvrage1);
        reservation2.setUser(user5);
        Reservation reservation3 = new Reservation();
        reservation3.setOuvrage(ouvrage1);
        reservation3.setUser(user6);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
        reservationRepository.save(reservation);

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