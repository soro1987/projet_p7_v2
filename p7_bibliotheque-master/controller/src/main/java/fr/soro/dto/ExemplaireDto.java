package fr.soro.dto;

import fr.soro.entities.Bibliotheque;
import fr.soro.entities.Emprunt;
import fr.soro.entities.Ouvrage;
import fr.soro.entities.User;
import lombok.Data;

@Data
public class ExemplaireDto {

    private Long id;

    private boolean disponible;

    private Ouvrage ouvrage;

    private Bibliotheque bibliotheque;

    private User user;

    private Emprunt emprunt;

}
