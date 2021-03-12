package fr.soro.service;

import fr.soro.entities.EarliestReturnDate;
import fr.soro.entities.Emprunt;
import fr.soro.entities.Exemplaire;
import fr.soro.entities.Ouvrage;
import fr.soro.repositories.EarliestReturnDateRepository;
import fr.soro.repositories.ExemplaireRepository;
import org.hibernate.validator.internal.metadata.aggregated.ExecutableMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class EarliestReturnDateService {

    @Autowired
    ExemplaireRepository exemplaireRepository;

    @Autowired
    EarliestReturnDateRepository earliestReturnDateRepository;

    public Date computeEarliestReturnDate(Ouvrage ouvrage){
        // get all examplaire for particular ouvrage which is not available,
        List<Exemplaire> exemplaireList = exemplaireRepository.findAllByOuvrageAndDisponible(ouvrage, false);

        // create list of emprunts that are not null
        // get the emprunt object
        List<Emprunt> emprunts =getEmpruntsNotNull(exemplaireList);

        // sort them by datecheance in ascending order, fetch the top one

        Emprunt topEmpruntByDate = getTopEmpruntByDate(emprunts);

        // fetch the EarliestReturnDate for this book and update it with new date
        return fetchAndUpdateEarliestReturnDate(topEmpruntByDate,ouvrage);
    }

    private Date fetchAndUpdateEarliestReturnDate(Emprunt topEmpruntByDate, Ouvrage ouvrage){
        if(topEmpruntByDate != null){
            EarliestReturnDate earliest = earliestReturnDateRepository.findByOuvrage(ouvrage);
            if(earliest != null){
                earliest.setExpectedReturnDate(topEmpruntByDate.getDateEcheance());
                earliestReturnDateRepository.save(earliest);
            }
            else {
                earliest = new EarliestReturnDate(ouvrage, topEmpruntByDate.getDateEcheance());
                earliestReturnDateRepository.save(earliest);
            }
            return topEmpruntByDate.getDateEcheance();
        }
        else {
            return null;
        }
    }

    private Emprunt getTopEmpruntByDate(List<Emprunt> emprunts){
        Collections.sort(emprunts);
        Emprunt topEmpruntByDate = null;
        if(emprunts.size() > 0) {
            topEmpruntByDate = emprunts.get(0);
        }
        return topEmpruntByDate;
    }

    private List<Emprunt> getEmpruntsNotNull(List<Exemplaire> exemplaireList){
        List<Emprunt> emprunts = new ArrayList<>();
        for (Exemplaire exemplaire : exemplaireList) {
            if(exemplaire.getEmprunt() != null){
                emprunts.add(exemplaire.getEmprunt());
            }
        }
        return emprunts;
    }
}
