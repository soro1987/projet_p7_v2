package fr.soro.batch.modele;

import java.io.Serializable;
import java.util.Date;

public class EmpruntDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4491642697428777517L;
	private Long id;
	private Date dateDebut;
	private Date dateEcheance;
	private boolean prolongation;
	private int depassement;
	private UserDto user;
	
	
	public EmpruntDto() {
		super();
	}


	public EmpruntDto(Long id, Date dateDebut, Date dateEcheance, boolean prolongation, int depassement, UserDto user) {
		super();
		this.id = id;
		this.dateDebut = dateDebut;
		this.dateEcheance = dateEcheance;
		this.prolongation = prolongation;
		this.depassement = depassement;
		this.user = user;
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


	public UserDto getUser() {
		return user;
	}


	public void setUser(UserDto user) {
		this.user = user;
	}
	
	

}