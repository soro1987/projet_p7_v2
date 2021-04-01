package fr.soro;

import fr.soro.entities.Emprunt;
import fr.soro.entities.Exemplaire;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.User;
import fr.soro.repositories.EmpruntRepository;
import fr.soro.repositories.ExemplaireRepository;
import fr.soro.repositories.OuvrageRepository;
import fr.soro.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class EmpruntServiceTest {

    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private OuvrageRepository ouvrageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmpruntRepository empruntRepository;
    @Autowired
    private MockMvcBuilder mockMvcBuilder;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = mockMvcBuilder.build();
    }

    @Test
    public void shouldExtendDateEcheance() throws Exception {
        Emprunt emprunt = new Emprunt();
        Date dateDebut = Date.from(LocalDate.now().minusDays(27).atStartOfDay(ZoneId.systemDefault()).toInstant());
        emprunt.setDateDebut(dateDebut);
        Date dateEcheance = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        emprunt.setDateEcheance(dateEcheance);
        User user = new User();
        user.setPrenom("soro");
        emprunt.setUser(userRepository.save(user));
        Exemplaire exemplaire = new Exemplaire();
        Ouvrage ouvrage = new Ouvrage();
        ouvrage.setTitre("rocky");
        Emprunt empruntSave = empruntRepository.save(emprunt);
        exemplaire.setEmprunt(empruntSave);
        exemplaire.setOuvrage(ouvrageRepository.save(ouvrage));
        exemplaireRepository.save(exemplaire);

        mockMvc.perform(MockMvcRequestBuilders.put("/emprunts/prolongation/{idEmprunt}",empruntSave.getId()));
        Emprunt emprunt1 = empruntRepository.findById(empruntSave.getId()).orElseThrow(RuntimeException::new);

        Assertions.assertThat(emprunt1.getDateEcheance()).isEqualToIgnoringHours(Date.from(dateEcheance.toInstant().plus(28, ChronoUnit.DAYS)));
    }

}
