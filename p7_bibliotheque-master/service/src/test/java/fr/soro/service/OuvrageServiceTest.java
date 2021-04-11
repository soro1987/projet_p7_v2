package fr.soro.service;

import fr.soro.entities.Emprunt;
import fr.soro.entities.User;
import fr.soro.repositories.EmpruntRepository;
import fr.soro.repositories.ExemplaireRepository;
import fr.soro.repositories.OuvrageRepository;
import fr.soro.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

class OuvrageServiceTest {
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





}