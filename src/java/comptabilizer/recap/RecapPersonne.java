/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comptabilizer.recap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Nicolas
 */
public class RecapPersonne {
    
    public int id_personne = 0;
    public Map<Integer, Float> dettes = new HashMap<Integer, Float>();
    
    public RecapPersonne(int id) {
        id_personne = id;
    }
    
    public RecapPersonne(int id, EntityManager em) {
        id_personne = id;
        
        Query createQuery = em.createNativeQuery("select f.payeur_id as payeur, sum(f.total * p.pourcentage) as somme from Facture f, Participation p where f.id = p.facture_id and p.personne_id = " + id + " and p.fait = 0 group by f.payeur_id");
        List<Object[]> resultList = createQuery.getResultList();
        
        for(Object[] obj : resultList) {
            dettes.put((Integer) obj[0], ((Double) obj[1]).floatValue() / 100);
        }
    }
    
    public Map<Integer, Float> getDettes() {
        return dettes;
    }
    
    
    public int getIdPersonne() {
        return id_personne;
    }
    
}
