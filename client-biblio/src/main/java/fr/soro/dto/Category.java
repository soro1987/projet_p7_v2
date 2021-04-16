package fr.soro.dto;

public class Category {

    public String bd = "Bande-dessiner";
    public String roman = "roman";
    public String scolaire = "scolaire";


    public Category() {
    }

    public Category(String bd, String roman, String scolaire) {
        this.bd = bd;
        this.roman = roman;
        this.scolaire = scolaire;
    }

    public String getBd() {
        return bd;
    }


    public void setBd(String bd) {
        this.bd = bd;
    }

    public String getRoman() {
        return roman;
    }

    public void setRoman(String roman) {
        this.roman = roman;
    }

    public String getScolaire() {
        return scolaire;
    }

    public void setScolaire(String scolaire) {
        this.scolaire = scolaire;
    }
}
