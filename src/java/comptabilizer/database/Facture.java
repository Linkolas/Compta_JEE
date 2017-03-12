/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comptabilizer.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nicolas
 */
@Entity
public class Facture implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String libelle = ""; 
    private float total = 0;
    
    @ManyToOne
    @NotNull
    private Personne payeur; 

    @OneToMany(mappedBy="facture", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Participation> participations = new ArrayList<Participation>();
    
    public Facture() {
    }

    public Facture(String libelle, float total, Personne payeur) {
        this.libelle = libelle;
        this.total = total;
        this.payeur = payeur;
    }
    
    public int getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Personne getPayeur() {
        return payeur;
    }

    public void setPayeur(Personne payeur) {
        this.payeur = payeur;
    }

    public Collection<Participation> getParticipations() {
        return participations;
    }

    @Override
    public String toString() {
        return "Facture{" + "id=" + id + ", libelle=" + libelle + ", total=" + total + ", payeur=" + payeur.getId() + '}';
    }
}
