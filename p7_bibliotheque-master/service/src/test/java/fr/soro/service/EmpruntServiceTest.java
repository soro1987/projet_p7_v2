package fr.soro.service;

import fr.soro.entities.Emprunt;
import fr.soro.entities.Exemplaire;
import fr.soro.entities.User;
import fr.soro.repositories.EmpruntRepository;
import fr.soro.repositories.ExemplaireRepository;
import fr.soro.repositories.OuvrageRepository;
import fr.soro.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class EmpruntServiceTest {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private EmpruntRepository empruntRepository;
    private UserRepository userRepository ;
    private ExemplaireRepository exemplaireRepository ;
    private OuvrageRepository ouvrageRepository ;

    EmpruntService empruntService;

    @BeforeEach
    void init() {
        empruntRepository = Mockito.mock(EmpruntRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        exemplaireRepository = Mockito.mock(ExemplaireRepository.class);
        ouvrageRepository = Mockito.mock(OuvrageRepository.class);
        empruntService = new EmpruntService(empruntRepository,userRepository,exemplaireRepository,ouvrageRepository);

    }

    @AfterEach
    void teardown() {
    }

    @Test
    public void shouldReturnEmprundGivenId(){
        //Given
        Emprunt emprunt = new Emprunt();
        emprunt.setId(1L);
        Mockito.doReturn(Optional.of(emprunt)).when(empruntRepository).findById(1L);
        //When
        Emprunt emprunt1 = this.empruntService.get(1L);
        //Then
        Assertions.assertThat(emprunt1.getId()).isEqualTo(1L);
    }

    @Test
    public void shouldReturnAllExpireEmprunts(){
        //Given
        //Set list Emprunt that have one Emprunt with dateEcheance before dateDebut so emprunt is expire
        //And the second Emprunt with dateDebut before dateEcheance so emprunt is not expire
        Emprunt emprunt1 = new Emprunt();
        emprunt1.setDateDebut(new Date(2021, Calendar.MARCH,2));
        emprunt1.setDateEcheance(new Date(2021, Calendar.MARCH,1));
        Emprunt emprunt2 = new Emprunt();
        emprunt2.setDateDebut(new Date(2021, Calendar.MARCH,1));
        emprunt2.setDateEcheance(new Date(2021, Calendar.MARCH,2));
        List<Emprunt> emprunts = new ArrayList<Emprunt>();
        emprunts.add(emprunt1);
        emprunts.add(emprunt2);
        //Mock empruntRepository
        Mockito.doReturn(emprunts).when(empruntRepository).findAll();
        //When
        List<Emprunt> empruntsExpirer = empruntRepository.findAll();
        //Then
        Assertions.assertThat(empruntsExpirer.get(0).getDateDebut()).isAfter(empruntsExpirer.get(0).getDateEcheance());
    }

//    @Ignore
//    @Test
//    public void shouldSaveEmprunt(){
//        User userId1 = new User();
//        userId1.setId(1L);
//        Exemplaire exemplaire = new Exemplaire();
//        exemplaire.setId(1L);
//        Mockito.doReturn(Optional.of(userId1)).when(userRepository).findById(1L);
//        Mockito.doReturn(exemplaire).when(exemplaireRepository).getExemplaireById(1L);
//        Emprunt emprunt = new Emprunt();
//        emprunt.setId(1L);
//        Mockito.doReturn(emprunt).when(empruntRepository).save(Mockito.isA(Emprunt.class));
//
//        Emprunt empruntResponse = empruntService.save(1L,1L);
//
//        Assertions.assertThat(
//
//    }

    @Test
    public void shouldSetEmpruntDateEcheance(){
        //Given
        Emprunt emprunt1 = new Emprunt();
        Date currentDate = new Date();
        //Add delay to current date corresponding at dateEcheance original delai-1
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.HOUR, 24*27);
        Date currentDatePlus27 = c.getTime();
        System.out.println(dateFormat.format(currentDatePlus27));
        //When
        this.empruntService.setDateEcheance(emprunt1);
        System.out.println(dateFormat.format(emprunt1.getDateEcheance()));
        //Then
        Assertions.assertThat(emprunt1.getDateEcheance()).isAfter(currentDatePlus27);
    }

    @Test
    public void shouldReturnUserEmprundGivenUserId(){
        //Given
        //List Emprunt of diferent users
        User userId1 = new User();
        userId1.setId(1L);
        User userId2 = new User();
        userId2.setId(2L);
        Emprunt emprunt1 = new Emprunt();
        emprunt1.setUser(userId1);
        Emprunt emprunt2 = new Emprunt();
        emprunt2.setUser(userId2);
        List<Emprunt> allEmprunts = new ArrayList<Emprunt>();
        allEmprunts.add(emprunt1);
        allEmprunts.add(emprunt2);
        //Mock empruntRepository
        Mockito.doReturn(allEmprunts).when(empruntRepository).findAll();
        //When
        List<Emprunt> empruntsUser1 = empruntService.getUserEmprunt(userId1.getId());
        //Then
        Assertions.assertThat(empruntsUser1.get(0).getUser().getId()).isEqualTo(1L);
    }

    @Test
    public void shouldIncreaseDateEcheanceBy24Day(){
        Emprunt emprunt1 = new Emprunt();
        emprunt1.setDateEcheance((new Date(2021, Calendar.MARCH,1)));
        Mockito.doReturn(Optional.of(emprunt1)).when(empruntRepository).findById(1L);
        //When
        Emprunt empruntResponse = empruntService.setProlongation(1L);
        //Then
        Assertions.assertThat(empruntResponse.getDateEcheance())
                .isEqualToIgnoringHours((new Date(2021, Calendar.MARCH,29)));
    }



}