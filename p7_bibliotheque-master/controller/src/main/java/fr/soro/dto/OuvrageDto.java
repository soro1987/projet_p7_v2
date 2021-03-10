package fr.soro.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OuvrageDto {

    private Long id;

    private String titre;

    private String auteur;

    private Date dateParution;

    private String description;

    private String categorie;

    private  int nbreExemplaireDispo;

    private String imageUrl;

}
