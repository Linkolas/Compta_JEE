<%-- 
    Document   : CreerPersonne
    Created on : 5 mars 2017, 00:45:06
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
        <title>Enregistrer une personne</title>
    </head>
    <body>

    <h1>Creer une personne</h1>
    <form id="form_creerpersonne" action="/WebJpa/personnes/creer" method="post">
    <table>
        <tr><td>Nom</td><td><input type="text" name="nom" /></td></tr>
    </table>
    <input type="submit" value="Enregistrer" />
    </form>
<a href="../personnes"><strong>Retourner à la liste des personnes</strong></a>
</body>
</html>
