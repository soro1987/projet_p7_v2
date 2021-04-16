package fr.soro.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DefaultDeserializationContext;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import fr.soro.dto.CreateReservationDto;
import fr.soro.entities.*;
import fr.soro.repositories.*;
import fr.soro.utils.TestUtils;
import org.assertj.core.api.Assertions;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class EmpruntControllerItTest {

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
    public void shouldCreateEmprunt() throws Exception {
        User user = userRepository.save(buildUser());
        Exemplaire exemplaire1 = new Exemplaire();
        exemplaireRepository.save(exemplaire1);
        exemplaire1.setDisponible(true);
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/emprunts/1/14")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        perform.andExpect(MockMvcResultMatchers.status().isCreated());
        Assertions.assertThat(perform.andReturn().getResponse().getHeader("Location")).isEqualTo("http://localhost/emprunts/11");

    }

    @Test
    public void shouldSetEmpruntProlongation() throws Exception {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateDebut(new Date(2021, Calendar.FEBRUARY, 1));
        emprunt.setDateEcheance(new Date(2021, Calendar.MARCH, 1));
        empruntRepository.save(emprunt);
        mockMvc.perform(MockMvcRequestBuilders.put("/emprunts/prolongation/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldDeleteEmprunt() throws Exception {
        Emprunt emprunt = empruntRepository.save(buildListEmprunts().get(0));
        Exemplaire exemplaire1 = new Exemplaire();
        exemplaire1.setOuvrage(buildOuvrage().get(0));
        exemplaire1.setDisponible(true);
        emprunt.getExemplaires().add(exemplaire1);
        exemplaireRepository.save(exemplaire1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/emprunts/delete/{empruntId}/{exemplaireId}", emprunt.getId(), exemplaire1.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isGone());
    }

    @Test
    public void shoulReturnListEmprundExpire() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/emprunts/expired")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(new ObjectMapper().readValue(perform.andReturn().getResponse().getContentAsString(),List.class)).hasSize(2);
    }

    @Test
    public void shoulReturnListUserWithEmprundExpire() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/emprunts/user/expired")
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(new ObjectMapper().readValue(perform.andReturn().getResponse().getContentAsString(),List.class)).hasSize(2);
    }

 @Test
    public void shoulReturnListEmprundByUser() throws Exception {
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/emprunts-user/{idUser}",1)
                .contentType(MediaType.APPLICATION_JSON_VALUE));
        perform.andExpect(MockMvcResultMatchers.status().isFound());
        Assertions.assertThat(new ObjectMapper().readValue(perform.andReturn().getResponse().getContentAsString(),List.class)).hasSize(10);
    }

    @Test
    public void shouldGetUserEmprunt() {

    }

    public void setupUserEmpruntInH2DB() {
        //Create ou user to build our reservation
        User user = new User(); //Our user
        user.setEmail("test@gmail.com");
        user.setUsername("testUser");
        user.setPassword("0000");
        userRepository.save(user);

        //Create and persist needed ouvrage
        Ouvrage ouvrage1 = new Ouvrage();
        ouvrage1.setAuteur("Auteur1");
        ouvrage1.setTitre("Titre1");
        ouvrage1.setCategorie("Roman");
        ouvrage1.setNbreExemplaireDispo(3);   //Create and persist needed ouvrage
        Ouvrage ouvrage2 = new Ouvrage();
        ouvrage1.setAuteur("Auteur2");
        ouvrage1.setTitre("Titre2");
        ouvrage1.setCategorie("Roman");
        ouvrage1.setNbreExemplaireDispo(3);   //Create and persist needed ouvrage
        Ouvrage ouvrage3 = new Ouvrage();
        ouvrage1.setAuteur("Auteur3");
        ouvrage1.setTitre("Titre3");
        ouvrage1.setCategorie("Roman");
        ouvrage1.setNbreExemplaireDispo(3);

        //Create Exemplaires of the particular ouvrage to build needed emprunt
        List<Exemplaire> exemplairesList1 = new ArrayList<Exemplaire>();
        Exemplaire exemplaire1 = new Exemplaire();
        exemplaire1.setOuvrage(ouvrage1);
        exemplairesList1.add(exemplaire1);
        List<Exemplaire> exemplairesList2 = new ArrayList<Exemplaire>();
        Exemplaire exemplaire2 = new Exemplaire();
        exemplaire2.setOuvrage(ouvrage2);
        exemplairesList2.add(exemplaire2);
        List<Exemplaire> exemplairesList3 = new ArrayList<Exemplaire>();
        Exemplaire exemplaire3 = new Exemplaire();
        exemplaire3.setOuvrage(ouvrage3);
        exemplairesList3.add(exemplaire3);

        //Create and persist emprunts containing exemplaire of the particular ouvrage
        //To calcule earliest return date
        Emprunt emprunt1 = new Emprunt();
        emprunt1.setDateEcheance(new Date(2021, Calendar.MARCH, 1));
        emprunt1.setExemplaires(exemplairesList1);
        emprunt1.setUser(user);
        Emprunt emprunt2 = new Emprunt();
        emprunt2.setDateEcheance(new Date(2021, Calendar.MARCH, 2));
        emprunt2.setExemplaires(exemplairesList2);
        emprunt2.setUser(user);
        Emprunt emprunt3 = new Emprunt();
        emprunt3.setDateEcheance(new Date(2021, Calendar.MARCH, 3));
        emprunt3.setExemplaires(exemplairesList3);
        emprunt3.setUser(user);
        empruntRepository.save(emprunt1);
        empruntRepository.save(emprunt2);
        empruntRepository.save(emprunt3);

    }



    public static User buildUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setUsername("testUser");
        user.setPassword("0000");
        return user;
    }

    public static List<Ouvrage> buildOuvrage() {
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

    public static Reservation buildReservation() {
        Reservation reservation = new Reservation();
        reservation.setOuvrage(buildOuvrage().get(0));
        reservation.setUser(buildUser());
//        reservation.setDateReservation(LocalDateTime.now());
        return reservation;
    }

    public static List<Emprunt> buildListEmprunts() {
        List<Emprunt> empruntList = new ArrayList<>();
        Emprunt emprunt1 = new Emprunt();
        emprunt1.setDateEcheance(new Date(2021, Calendar.MARCH, 1));
        emprunt1.setExemplaires(buildListExemplaire());
        Emprunt emprunt2 = new Emprunt();
        emprunt2.setDateEcheance(new Date(2021, Calendar.MARCH, 2));
        empruntList.add(emprunt1);
        empruntList.add(emprunt2);
        emprunt1.setExemplaires(buildListExemplaire());
        return empruntList;
    }

    public static List<Exemplaire> buildListExemplaire() {
        List<Exemplaire> exemplaires = new ArrayList<Exemplaire>();
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setOuvrage(buildOuvrage().get(0));
        exemplaires.add(exemplaire);
        return exemplaires;
    }

}
