/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comptabilizer.servlet;

import comptabilizer.database.Personne;
import comptabilizer.recap.RecapPersonne;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 *
 * @author Nicolas
 */
@WebServlet(name = "Recap", urlPatterns = {"/Recap"})
public class Recap extends HttpServlet {

    @PersistenceUnit
    private EntityManagerFactory emf;  
    
    @Resource
    private UserTransaction utx;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        assert emf != null;  //Make sure injection went through correctly.
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            
            List<Personne> personnes = em.createQuery("select f from Personne f", Personne.class).getResultList();
            Map<Integer, Personne> personnes_map = new HashMap<Integer, Personne>();
            Map<Integer, RecapPersonne> recaps_map = new HashMap<Integer, RecapPersonne>();
            Map<Integer, RecapPersonne> recapsfinal_map = new HashMap<Integer, RecapPersonne>();
            
            for(Personne personne : personnes) {
                RecapPersonne rp = new RecapPersonne(personne.getId(), em);
                RecapPersonne rp2 = new RecapPersonne(personne.getId(), em);
                
                personnes_map.put(personne.getId(), personne);
                recaps_map.put(personne.getId(), rp);
                recapsfinal_map.put(personne.getId(), rp2);
            }
            
            
            for(RecapPersonne rp : recaps_map.values()) {
                for(Map.Entry<Integer, Float> entry : rp.dettes.entrySet()) {
                    
                    if(recapsfinal_map.get(entry.getKey()).dettes.containsKey(rp.id_personne)) {
                        float current = recapsfinal_map.get(entry.getKey()).dettes.get(rp.id_personne);
                        float substract = entry.getValue();
                        
                        if((current - substract) > 0) {
                            recapsfinal_map.get(entry.getKey()).dettes.replace(rp.id_personne, current - substract);
                        } else {
                            recapsfinal_map.get(entry.getKey()).dettes.remove(rp.id_personne);
                        }
                    }
                    
                }
            }
            
            request.setAttribute("personnes_map", personnes_map);
            request.setAttribute("recaps_map", recaps_map);
            request.setAttribute("recapsfinal_map", recapsfinal_map);
            request.getRequestDispatcher("/Recap.jsp").forward(request, response);
        } finally {
            //close the em to release any resources held up by the persistebce provider
            if(em != null) {
                em.close();
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
