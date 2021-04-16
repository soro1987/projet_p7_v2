package fr.soro.dto;


import java.util.Date;



public class EmpruntDto {

    private Long id;

    private Date dateDebut;//LocaleDate

    private Date dateEcheance;

    private boolean prolongation;

    private int depassement;

    private boolean extendable;


    public EmpruntDto() {
    }

    public EmpruntDto(Long id, Date dateDebut, Date dateEcheance, boolean prolongation, int depassement, boolean isExtendable) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateEcheance = dateEcheance;
        this.prolongation = prolongation;
        this.depassement = depassement;
        this.extendable = isExtendable;
    }

    public boolean isExtendable() {
        return extendable;
    }

    public void setExtendable(boolean extendable) {
        this.extendable = extendable;
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
}
