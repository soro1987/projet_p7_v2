package fr.soro.entities;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "exemplaire")
public class Exemplaire implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private boolean disponible;

	@JsonBackReference(value = "ouvr-ex")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ouvrage")
	private Ouvrage ouvrage;

	@JsonBackReference(value = "ouvr-bib")

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "bibliotheque")
	private Bibliotheque bibliotheque;
	@JsonBackReference(value = "em-user")
	private User sysUser;

	@JsonBackReference(value = "ex-emp")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "emprunt_id")
	private Emprunt emprunt;

	public Exemplaire() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Exemplaire(Long id, boolean disponible, Ouvrage ouvrage, Bibliotheque bibliotheque, Emprunt emprunt) {
		super();
		this.id = id;
		this.disponible = disponible;
		this.ouvrage = ouvrage;
		this.bibliotheque = bibliotheque;
		this.emprunt = emprunt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public Emprunt getEmprunt() {
		return emprunt;
	}
	public void setEmprunt(Emprunt emprunt) {
		this.emprunt = emprunt;
	}

	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public User getSysUser() {
		return sysUser;
	}

	public void setSysUser(User sysUser) {
		this.sysUser = sysUser;
	}



}