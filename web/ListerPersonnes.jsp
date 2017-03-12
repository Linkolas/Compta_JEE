<%-- 
    Document   : ListerPersonnes
    Created on : 5 mars 2017, 00:43:26
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
        <title>Liste des personnes</title>
    </head>
    <body>

    <h1>Liste des personnes enregistrées</h1>
    
<table id="table_personnes" border="3">
<tr >
    <th bgcolor=>ID</th>
    <th bgcolor=>Nom</th>
</tr>
<c:forEach var="personne" begin="0" items="${requestScope.liste_personnes}">
<tr>
    <td>${personne.id}&nbsp;&nbsp;</td> 
    <td>${personne.nom}&nbsp;&nbsp;</td> 
</tr>
</c:forEach>

</table>
<a href="/WebJpa/personnes/creer"><strong>Créer une personne</strong></a><br/>
<a href="/WebJpa/"><strong>Retour à l'index</strong></a>
</body>
</html>
