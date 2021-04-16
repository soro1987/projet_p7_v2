package fr.soro.dto;

import java.io.Serializable;
import java.util.List;



public class BiblioDto implements Serializable{
  
	/**
	 * 
	 */
	private static final long serialVersionUID = -6424731632713157049L;
	
	private Long id;
	
	private String nom;
	
	private String adresse;
	
	private String telephone;
	
	private String email;
	
	private String description;
	
	List<ExemplaireDto> exemplaires;

	public BiblioDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BiblioDto(Long id, String nom, String adresse, String telephone, String email, String description,
			List<ExemplaireDto> exemplaires) {
		super();
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.telephone = telephone;
		this.email = email;
		this.description = description;
		this.exemplaires = exemplaires;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ExemplaireDto> getExemplaires() {
		return exemplaires;
	}

	public void setExemplaires(List<ExemplaireDto> exemplaires) {
		this.exemplaires = exemplaires;
	}
	
	
}
