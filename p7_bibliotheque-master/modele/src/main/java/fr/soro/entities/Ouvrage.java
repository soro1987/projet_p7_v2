package fr.soro.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "ouvrage")
@Data
public class Ouvrage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titre;
	private String auteur;
	private Date dateParution;
	private String description;
	private String categorie;
	private  int nbreExemplaireDispo=0;
	private String imageUrl;

	@Lob
	private byte[] image;
	@JsonManagedReference(value = "ouvr-ex")
	@OneToMany(mappedBy = "ouvrage", fetch = FetchType.LAZY)
	private List<Exemplaire> exemplaires;

	public void setNbreExemplaireDispo() {
		if(this.exemplaires != null) {
			for(Exemplaire expl: this.exemplaires) {
				if( expl.isDisponible()) {
					this.nbreExemplaireDispo++;
				}
			}
		}
	}

	public void decrease(){
		this.nbreExemplaireDispo--;
	}

	public void increase(){
		this.nbreExemplaireDispo++;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<Exemplaire> getExemplaires() {
		return exemplaires;
	}

	public void setExemplaires(List<Exemplaire> exemplaires) {
		this.exemplaires = exemplaires;
	}
}
