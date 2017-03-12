<%-- 
    Document   : CreerFactures
    Created on : 4 mars 2017, 23:57:18
    Author     : Nicolas
--%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Enregistrer une facture</title>
    </head>
    <body>

    <h1>Créer une facture</h1>
    <form id="form_creerfacture" action="/WebJpa/factures/creer" method="post">
        <table>
            <tr><td>Libellé</td><td><input type="text" name="libelle" value="${facture.getLibelle()}"/></td></tr>
            <tr><td>Total</td><td><input type="number" step="0.01" min="0" name="total" value="${facture.getTotal()}"/></td></tr>
            <tr>
                <td>Payeur</td>
                <td>
                    <select id="payeur" name="payeur">
                        <c:forEach var="personne" begin="0" items="${requestScope.liste_personnes}">
                            <option value="${personne.id}" <c:if test="${facture.getPayeur().getId() == personne.id}"> SELECTED </c:if>>${personne.nom}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
        </table>
        <br/><br/>
        Participants :
        <table id="participants">
            <tr>
                <th>Nom</th>
                <th>Pourcentage</th>
                <th>Valeur</th>
                <th></th>
            </tr>
            <tr>
                <td>Payeur</td>
                <td><input id="pourcent_payeur" value="100" DISABLED/>%</td>
                <td><input id="valeur_payeur" value="${(facture != null ? facture.getTotal() : 0)}" type="number" step="0.01" min="0" DISABLED></td>
                <td></td>
            </tr>
            <c:forEach var="participation" begin="0" items="${participations}">
                <tr>
                    <td>
                        <select name="participant_id">
                        <c:forEach var="personne" begin="0" items="${requestScope.liste_personnes}">
                            <option value="${personne.id}" <c:if test="${participation.getPersonne().getId() == personne.id}"> SELECTED </c:if>>${personne.nom}</option>
                        </c:forEach>
                        </select>
                    </td>
                    <td><input class="pourcentage" type="number" step="0.01" min=0 max=100 name="participant_pourcent" value="${participation.getPourcentage()}" onkeyup="onPercentChange(this)"/>%</td>
                    <td><input class="valeur_participant" type="number" step="0.01" min="0" value="${participation.getPourcentage()/100 * facture.getTotal()}" onkeyup="onValueChange(this)"/></td>
                    <td><button type="button" onclick="deleteTableRow(this)">Supprimer</button></td>
                </tr>
            </c:forEach>
        </table>
        
        <button type="button" id="add_parts">Ajouter participant</button>
        <br/>
        <c:if test="${facture != null}">
            <input type="hidden" name="id_facture" value="${facture.getId()}"/>
        </c:if>
        
        <label><input type="checkbox" id="isAutomatique" <c:if test="${facture == null}">CHECKED</c:if>/>Parts automatiques</label><br/>
        <br/><input type="submit" value="Enregistrer" />
    </form>
            
    <c:if test="${facture != null}">
        <a href="/WebJpa/factures/creer?delete=${facture.id}">Supprimer</a><br/>
    </c:if>
    <a href="../factures"><strong>Retourner à la liste des factures</strong></a>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="../js/creerfacture.js"></script>
</html>

