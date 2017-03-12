<%-- 
    Document   : Recap
    Created on : 9 mars 2017, 22:37:02
    Author     : Nicolas
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Récapitulatif</title>
    </head>
    <body>
        <a href="/WebJpa/factures">Factures</a><br/>
        <a href="/WebJpa/personnes">Personnes</a>
        
        <table>
            <tr>
                <th></th>
                <c:forEach var="personne_colonne" items="${personnes_map.values()}">
                    <th>${personne_colonne.getNom()}</th>
                </c:forEach>
            </tr>
            <c:forEach var="personne_ligne" items="${personnes_map.values()}">
                <tr>
                    <td>${personne_ligne.getNom()}</td>
                    <c:forEach var="personne_colonne" items="${personnes_map.values()}">
                        <td>
                            <c:choose>
                                <c:when test="${recaps_map.get(personne_ligne.getId()).dettes.containsKey(personne_colonne.getId())}">
                                    ${recaps_map.get(personne_ligne.getId()).dettes.get(personne_colonne.getId())}
                                </c:when>    
                                <c:otherwise>
                                    0
                                </c:otherwise>
                            </c:choose>
                            €
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        
        <c:forEach var="recapfinal" items="${recapsfinal_map.values()}">
            <c:if test="${recapfinal.dettes.size() > 0}">
                
                <c:forEach var="dette" items="${recapfinal.dettes.entrySet()}">
                    ${personnes_map.get(recapfinal.getIdPersonne()).getNom()} doit ${dette.getValue()} € à ${personnes_map.get(dette.getKey()).getNom()}.<br/>
                </c:forEach>
                    
            </c:if>
        </c:forEach>
        
    </body>
</html>
