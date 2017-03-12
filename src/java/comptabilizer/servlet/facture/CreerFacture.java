/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comptabilizer.servlet.facture;

import comptabilizer.database.Facture;
import comptabilizer.database.Participation;
import comptabilizer.database.Personne;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "CreerFacture", urlPatterns = {"/factures/creer"})
public class CreerFacture extends HttpServlet {

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
            //create an em. 
            //Since the em is created inside a transaction, it is associsated with 
            //the transaction
            
            if( (   !request.getParameterMap().keySet().contains("libelle")
                ||  !request.getParameterMap().keySet().contains("total"))
                &&   request.getParameter("delete") == null) {
                
                em = emf.createEntityManager();
            
                if(request.getParameter("id") != null) {
                    Facture facture = em.find(Facture.class, Integer.parseInt(request.getParameter("id")));
                    Query createQuery = em.createQuery("SELECT p FROM Participation p WHERE p.facture = :facture", Participation.class);
                    List<Participation> participations = createQuery.setParameter("facture", facture).getResultList();
                    
                    
                    request.setAttribute("facture", facture);
                    request.setAttribute("participations", participations);
                }
                
                List personnes = em.createQuery("select f from Personne f").getResultList();
                request.setAttribute("liste_personnes", personnes);

                request.getRequestDispatcher("/CreerFactures.jsp").forward(request, response);
                
            } else if (request.getParameter("delete") != null){ 
                
                utx.begin();
                
                em = emf.createEntityManager();
                int id = Integer.parseInt(request.getParameter("delete"));
                /*Query createQuery = em.createQuery("delete from Facture f WHERE f.id = :id");
                createQuery.setParameter("id", id);
                createQuery.executeUpdate();*/
                
                
                Facture facture = em.find(Facture.class, id);
                
                Query createQuery = em.createQuery("DELETE FROM Participation p WHERE p.facture = :facture");
                createQuery.setParameter("facture", facture).executeUpdate();
                
                em.remove(facture);
                
                utx.commit();
                
                request.getRequestDispatcher("../factures").forward(request, response);
                
            } else {

                
                //begin a transaction
                utx.begin();
                em = emf.createEntityManager();
                
                //Get the data from user's form
                String libelle    = (String) request.getParameter("libelle");
                float total       = Float.parseFloat(request.getParameter("total"));
                Personne payeur   = em.getReference(Personne.class, Integer.parseInt(request.getParameter("payeur")));
                String[] participants = request.getParameterValues("participant_id");
                String[] participants_pourcentages = request.getParameterValues("participant_pourcent");
                
                //Create a person instance out of it
                Facture facture;
                if(request.getParameter("id_facture") != null) {
                    facture = em.find(Facture.class, Integer.parseInt(request.getParameter("id_facture")));
                    facture.setLibelle(libelle);
                    facture.setTotal(total);
                    facture.setPayeur(payeur);
                    
                    Query createQuery = em.createQuery("DELETE FROM Participation p WHERE p.facture = :facture");
                    createQuery.setParameter("facture", facture).executeUpdate();
                } else {
                    facture = new Facture(libelle, total, payeur);
                }
                List<Participation> fps = new ArrayList<Participation>();
                if(participants != null) {
                    for(int i = 0; i < participants.length; i++) {
                        Participation fp = new Participation();
                        fp.setFacture(facture);
                        fp.setPersonne(em.getReference(Personne.class, Integer.parseInt(participants[i])));
                        fp.setPourcentage(Float.parseFloat(participants_pourcentages[i]));
                        fps.add(fp);
                    }
                }
                
                //persist the person entity
                em.persist(facture);
                for(Participation fp: fps) {
                    em.persist(fp);
                }
                //commit transaction which will trigger the em to 
                //commit newly created entity into database
                utx.commit();

                //Forward to ListPerson servlet to list persons along with the newly
                //created person above
                request.getRequestDispatcher("../factures").forward(request, response);
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
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
