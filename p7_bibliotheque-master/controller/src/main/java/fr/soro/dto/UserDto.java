package fr.soro.dto;

import lombok.Data;

@Data
public class UserDto {

    private String nom;

    private String prenom;

    private String email;

    private String telephone;

    private int user_active;

    private String password;

    private String username;


}
