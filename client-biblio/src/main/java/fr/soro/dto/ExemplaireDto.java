package fr.soro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;



public class ExemplaireDto implements Serializable {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 2055011366749582817L;

    private Long id;
	
	private boolean disponible;
	
	private OuvrageDto ouvrage;
	
	private BiblioDto bibliotheque;

	private UserDto user;
	
	private EmpruntDto emprunt;

	public ExemplaireDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ExemplaireDto(Long id, boolean disponible, OuvrageDto ouvrage, BiblioDto bibliotheque, UserDto user,
			EmpruntDto emprunt) {
		super();
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

	public OuvrageDto getOuvrage() {
		return ouvrage;
	}

	public void setOuvrage(OuvrageDto ouvrage) {
		this.ouvrage = ouvrage;
	}

	public BiblioDto getBibliotheque() {
		return bibliotheque;
	}

	public void setBibliotheque(BiblioDto bibliotheque) {
		this.bibliotheque = bibliotheque;
	}

	public UserDto getUser() {
		return user;
	}


	public void setUser(UserDto user) {
		this.user = user;
	}


	public EmpruntDto getEmprunt() {
		return emprunt;
	}


	public void setEmprunt(EmpruntDto emprunt) {
		this.emprunt = emprunt;
	}



}
