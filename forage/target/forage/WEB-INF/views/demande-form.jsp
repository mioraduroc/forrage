<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>

<h2>Formulaire Demande</h2>

<form:form method="POST" action="save" modelAttribute="demande">

    Reference :
    <form:input path="reference"/>
    <br><br>

    Lieu :
    <form:input path="lieu"/>
    <br><br>

    Client :
    <form:select path="client.id">
        <form:option value="" label="-- Choisir Client --"/>
        <form:options items="${clients}" itemValue="id" itemLabel="nom"/>
    </form:select>
    <br><br>

    Commune :
    <form:select path="commune.id">
        <form:option value="" label="-- Choisir Commune --"/>
        <form:options items="${communes}" itemValue="id" itemLabel="libelle"/>
    </form:select>
    <br><br>

    <input type="submit" value="Enregistrer"/>

</form:form>

</body>
</html>