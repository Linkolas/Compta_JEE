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
import javax.persistence.OneToMany;

/**
 *
 * @author Nicolas
 */
@Entity
public class Personne implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String nom = "SansNom";
    
    @OneToMany(mappedBy="payeur", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Facture> factures = new ArrayList<Facture>(); 
    
    @OneToMany(mappedBy = "personne", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Participation> participations = new ArrayList<Participation>();

    public Personne() {
    }

    public Personne(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Collection<Facture> getFactures() {
        return factures;
    }

    public Collection<Participation> getParticipations() {
        return participations;
    }
    
    
}
