/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comptabilizer.database;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/** 
 *
 * @author Nicolas
 */
@Entity
@IdClass(ParticipationID.class) 
public class Participation implements Serializable {
    
    @Id
    @ManyToOne
    private Facture facture;
    @Id
    @ManyToOne
    private Personne personne;
    
    @NotNull
    private float pourcentage;
    
    private boolean fait = false;

    public Participation() {
    }

    public Participation(Facture facture, Personne personne, float pourcentage) {
        this.facture = facture;
        this.personne = personne;
        this.pourcentage = pourcentage;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }

    public float getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(float pourcentage) {
        this.pourcentage = pourcentage;
    }

    public boolean isFait() {
        return fait;
    }

    public void setFait(boolean fait) {
        this.fait = fait;
    }    
}
