package fr.soro.dto;

import lombok.Data;


public class UserDto {

    private String nom;

    private String prenom;

    private String email;

    private String telephone;

    private int user_active;

    private String username;

    private String password;

    public UserDto() {
    }

    public UserDto(String nom, String prenom, String email, String telephone, int user_active, String password, String username) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.user_active = user_active;
        this.username = username;
    }


//    public UserDto(String nom, String prenom, String email, String telephone, int user_active, String username, String password) {
//        this.nom = nom;
//        this.prenom = prenom;
//        this.email = email;
//        this.telephone = telephone;
//        this.user_active = user_active;
//        this.username = username;
////        this.password = password;
//    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
