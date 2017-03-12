<%-- 
    Document   : ListerFactures
    Created on : 4 mars 2017, 23:40:27
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
        <title>Liste des factures</title>
    </head>
    <body>

    <h1>Liste des factures enregistrées</h1>
    
<table id="table_factures" border="3">
<tr >
    <th bgcolor=>ID</th>
    <th bgcolor=>Libellé</th>
    <th bgcolor=>Total</th>
    <th bgcolor=>Payeur</th>
    <th bgcolor=>Editer</th>
</tr>
<c:forEach var="facture" begin="0" items="${requestScope.liste_factures}">
<tr>
    <td>${facture.id}</td> 
    <td>${facture.libelle}</td> 
    <td>${facture.total}</td>
    <td>${facture.getPayeur().getNom()}</td>
    <td>
        <a href="/WebJpa/factures/creer?id=${facture.id}">Editer</a>
    </td>
</tr> 

</c:forEach>

</table>
<a href="/WebJpa/factures/creer"><strong>Créer une facture</strong></a><br/>
<a href="/WebJpa/"><strong>Retour à l'index</strong></a>
</body>
</html>
