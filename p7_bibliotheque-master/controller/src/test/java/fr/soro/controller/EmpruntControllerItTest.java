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
        mockMvc.perform(MockMvcRequestBuilders.post("/emprunts/1/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateDebut").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateEcheance").value("Titre1"));
    }

    @Test
    public void shouldSetEmpruntProlongation() throws Exception {
        Emprunt emprunt = new Emprunt();
        emprunt.setDateDebut(new Date(2021, Calendar.FEBRUARY,1));
        emprunt.setDateEcheance(new Date(2021, Calendar.MARCH,1));
        empruntRepository.save(emprunt);
        mockMvc.perform(MockMvcRequestBuilders.delete("emprunts/prolongation/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.dateEcheance").value(""));
    }

    @Test
    public void shouldGetUserEmprunt(){
        this.setupH2DB();
    }

    public void setupUserEmpruntInH2DB(){
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
        emprunt1.setDateEcheance(new Date(2021, Calendar.MARCH,1));
        emprunt1.setExemplaires(exemplairesList1);
        emprunt1.setUser(user);
        Emprunt emprunt2 = new Emprunt();
        emprunt2.setDateEcheance(new Date(2021, Calendar.MARCH,2));
        emprunt2.setExemplaires(exemplairesList2);
        emprunt2.setUser(user);
        Emprunt emprunt3 = new Emprunt();
        emprunt3.setDateEcheance(new Date(2021, Calendar.MARCH,3));
        emprunt3.setExemplaires(exemplairesList3);
        emprunt3.setUser(user);
        empruntRepository.save(emprunt1);
        empruntRepository.save(emprunt2);
        empruntRepository.save(emprunt3);

    }





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

        //Create Exemplaires of the particular ouvrage to build needed emprunt
        List<Exemplaire> exemplairesList1 = new ArrayList<Exemplaire>();
        Exemplaire exemplaire1 = new Exemplaire();
        exemplaire1.setOuvrage(ouvrage1);
        exemplairesList1.add(exemplaire1);
        List<Exemplaire> exemplairesList2 = new ArrayList<Exemplaire>();
        Exemplaire exemplaire2 = new Exemplaire();
        exemplaire2.setOuvrage(ouvrage1);
        exemplairesList2.add(exemplaire2);
        List<Exemplaire> exemplairesList3 = new ArrayList<Exemplaire>();
        Exemplaire exemplaire3 = new Exemplaire();
        exemplaire3.setOuvrage(ouvrage1);
        exemplairesList3.add(exemplaire3);

        //Create and persist emprunts containing exemplaire of the particular ouvrage
        //To calcule earliest return date
        Emprunt emprunt1 = new Emprunt();
        emprunt1.setDateEcheance(new Date(2021, Calendar.MARCH,1));
        emprunt1.setExemplaires(exemplairesList1);
        emprunt1.setUser(user1);
        Emprunt emprunt2 = new Emprunt();
        emprunt2.setDateEcheance(new Date(2021, Calendar.MARCH,2));
        emprunt2.setExemplaires(exemplairesList2);
        emprunt2.setUser(user2);
        Emprunt emprunt3 = new Emprunt();
        emprunt3.setDateEcheance(new Date(2021, Calendar.MARCH,3));
        emprunt3.setExemplaires(exemplairesList3);
        emprunt3.setUser(user3);
        empruntRepository.save(emprunt1);
        empruntRepository.save(emprunt2);
        empruntRepository.save(emprunt3);

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

        //Create and persist Exemplaires of the needed ouvrage for reservation List
        List<Exemplaire> exemplairesList4 = new ArrayList<Exemplaire>();
        Exemplaire exemplaire4 = new Exemplaire();
        exemplaire4.setOuvrage(ouvrage1);
        exemplairesList4.add(exemplaire4);
        List<Exemplaire> exemplairesList5 = new ArrayList<Exemplaire>();
        Exemplaire exemplaire5 = new Exemplaire();
        exemplaire5.setOuvrage(ouvrage1);
        exemplairesList5.add(exemplaire5);
        List<Exemplaire> exemplairesList6 = new ArrayList<Exemplaire>();
        Exemplaire exemplaire6 = new Exemplaire();
        exemplaire6.setOuvrage(ouvrage1);
        exemplairesList6.add(exemplaire6);
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
