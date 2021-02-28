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
	@OneToMany(mappedBy = "ouvrage", fetch = FetchType.EAGER)
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

/*	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}*/
}
