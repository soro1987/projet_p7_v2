package fr.soro.service;

import fr.soro.entities.Ouvrage;
import fr.soro.repositories.OuvrageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
@Service
public class OuvrageService {


    @Autowired
    private OuvrageRepository ouvrageRepository;

    public void uploadImage(byte[] image, Long id) {
        Ouvrage ouvrage = ouvrageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ouvrage " + id + " not found"));
        ouvrage.setImage(image);
        ouvrageRepository.saveAndFlush(ouvrage);
    }

    public List<Ouvrage> getByCategorie(String categorie) {
        return ouvrageRepository.findByCategorieContains(categorie);
    }

    public Ouvrage getOne(Long id) {
        return ouvrageRepository.findById(id).get();
    }

    public List<Ouvrage> getAll() {
        return ouvrageRepository.findAllWithExemplaires();
    }

    public List<Ouvrage> getByTitreAuteur(String motcle) {
        return ouvrageRepository.findByTitreAuteur("%"+motcle.toLowerCase()+"%","%"+motcle.toLowerCase()+"%");
    }

    public void delete(Long id) {
        this.ouvrageRepository.deleteById(id);
    }

    public Ouvrage save(Ouvrage ouvrage) {
        return this.ouvrageRepository.save(ouvrage);
    }


}
