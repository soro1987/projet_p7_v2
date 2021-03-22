package fr.soro.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "emprunt")
public class Emprunt implements Serializable, Comparable<Emprunt> {

	
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

//	@JsonBackReference(value = "em-user")
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
//	@JsonManagedReference(value = "ex-emp")
	@OneToOne
	@JoinColumn(name = "emprunt_id")
	private Exemplaire exemplaire;
	
	public Emprunt() {
		super();
	}

	public Emprunt(Long id, Date dateDebut, Date dateEcheance, boolean prolongation, int depassement, User user, Exemplaire exemplaire) {
		this.id = id;
		this.dateDebut = dateDebut;
		this.dateEcheance = dateEcheance;
		this.prolongation = prolongation;
		this.depassement = depassement;
		this.user = user;
		this.exemplaire = exemplaire;
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

	public Exemplaire getExemplaire() {
		return exemplaire;
	}

	public void setExemplaire(Exemplaire exemplaire) {
		this.exemplaire = exemplaire;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public int compareTo(Emprunt emprunt) {
		if(this.dateEcheance.before(emprunt.getDateEcheance())){
			return 1;
		}
		else if(this.dateEcheance.after(emprunt.getDateEcheance())){
			return -1;
		}
		return 0;
	}
}
