package fr.soro.dto;

import lombok.Data;

import java.util.Date;


public class OuvrageDto {

    private Long id;

    private String titre;

    private String auteur;

    private Date dateParution;

    private String description;

    private String categorie;

    private  int nbreExemplaireDispo;

    private String imageUrl;

    private byte[] image;

    public OuvrageDto() {
    }

    public OuvrageDto(Long id, String titre, String auteur, Date dateParution, String description, String categorie, int nbreExemplaireDispo, String imageUrl, byte[] image) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.dateParution = dateParution;
        this.description = description;
        this.categorie = categorie;
        this.nbreExemplaireDispo = nbreExemplaireDispo;
        this.imageUrl = imageUrl;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public Date getDateParution() {
        return dateParution;
    }

    public void setDateParution(Date dateParution) {
        this.dateParution = dateParution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getNbreExemplaireDispo() {
        return nbreExemplaireDispo;
    }

    public void setNbreExemplaireDispo(int nbreExemplaireDispo) {
        this.nbreExemplaireDispo = nbreExemplaireDispo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
