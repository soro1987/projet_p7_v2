package fr.soro.dto;

import fr.soro.entities.Bibliotheque;
import fr.soro.entities.Emprunt;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.User;
import lombok.Data;


public class ExemplaireDto {

    private Long id;

    private boolean disponible;

    private Ouvrage ouvrage;

    private Bibliotheque bibliotheque;

    private User user;

    private Emprunt emprunt;

    public ExemplaireDto() {
    }

    public ExemplaireDto(Long id, boolean disponible, Ouvrage ouvrage, Bibliotheque bibliotheque, User user, Emprunt emprunt) {
        this.id = id;
        this.disponible = disponible;
        this.ouvrage = ouvrage;
        this.bibliotheque = bibliotheque;
        this.user = user;
        this.emprunt = emprunt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Ouvrage getOuvrage() {
        return ouvrage;
    }

    public void setOuvrage(Ouvrage ouvrage) {
        this.ouvrage = ouvrage;
    }

    public Bibliotheque getBibliotheque() {
        return bibliotheque;
    }

    public void setBibliotheque(Bibliotheque bibliotheque) {
        this.bibliotheque = bibliotheque;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Emprunt getEmprunt() {
        return emprunt;
    }

    public void setEmprunt(Emprunt emprunt) {
        this.emprunt = emprunt;
    }
}
