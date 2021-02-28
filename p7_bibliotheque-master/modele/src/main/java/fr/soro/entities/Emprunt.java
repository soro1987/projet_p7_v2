package fr.soro.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "emprunt")
public class Emprunt implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date dateDebut;//LocaleDate
	private Date dateEcheance;
	private boolean prolongation;
	private int depassement;
	@JsonBackReference(value = "em-user")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@JsonManagedReference(value = "ex-emp")	
	@OneToMany(mappedBy = "emprunt")
	List<Exemplaire> exemplaires = new ArrayList<Exemplaire>();
	
	public Emprunt() {
		super();
	}

	public Emprunt(Long id, Date dateDebut, Date dateEcheance, boolean prolongation, int depassement, User user,
			List<Exemplaire> exemplaires) {
		super();
		this.id = id;
		this.dateDebut = dateDebut;
		this.dateEcheance = dateEcheance;
		this.prolongation = prolongation;
		this.depassement = depassement;
		this.user = user;
		this.exemplaires = exemplaires;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateEcheance() {
		return dateEcheance;
	}

	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}

	public boolean isProlongation() {
		return prolongation;
	}

	public void setProlongation(boolean prolongation) {
		this.prolongation = prolongation;
	}

	public int getDepassement() {
		return depassement;
	}

	public void setDepassement(int depassement) {
		this.depassement = depassement;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Exemplaire> getExemplaires() {
		return exemplaires;
	}

	public void setExemplaires(List<Exemplaire> exemplaires) {
		this.exemplaires = exemplaires;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
