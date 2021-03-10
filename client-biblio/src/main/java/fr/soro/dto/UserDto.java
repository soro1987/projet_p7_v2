package fr.soro.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class UserDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8899897687992575881L;

	Long id;

	private String nom;
	
	private String prenom;
	
	private String email;
	
	private String telephone;
	
	private int user_active = 1;
	
	private String login = "admin";

	private String username;

	private String password;
	
	private List<EmpruntDto> emprunts = new ArrayList<EmpruntDto>();

	private List<String> roles = new ArrayList<>();

	public UserDto() {
		super();
	}

	

	public UserDto(Long id, String nom, String prenom, String email, String telephone, int user_active, String login,
			String username, String password, List<String> roles) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.user_active = user_active;
		this.login = login;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public UserDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getUser_active() {
		return user_active;
	}

	public void setUser_active(int user_active) {
		this.user_active = user_active;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public String toString() {
		return "UserBean [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", telephone="
				+ telephone + ", user_active=" + user_active + ", login=" + login + ", username=" + username
				+ ", password=" + password + ", emprunts=" + emprunts + ", roles=" + roles + "]";
	}
	
	
	

}
