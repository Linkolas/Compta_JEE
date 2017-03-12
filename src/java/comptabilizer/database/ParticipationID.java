/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comptabilizer.database;

import java.io.Serializable;

/**
 *
 * @author Nicolas
 */
public class ParticipationID implements Serializable {
 
    private int facture;
    private int personne;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.facture;
        hash = 71 * hash + this.personne;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParticipationID other = (ParticipationID) obj;
        if (this.facture != other.facture) {
            return false;
        }
        if (this.personne != other.personne) {
            return false;
        }
        return true;
    }

}
