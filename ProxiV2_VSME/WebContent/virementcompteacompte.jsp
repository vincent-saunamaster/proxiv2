<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/style.css">
<title>Virement compte à compte</title>
</head>
<body>
	<section class="row">

	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<h1>ProxiBanqueV2</h1>
		<hr />
	</div>
	</section>
	<section class="row">

	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<h2>Virement compte à compte</h2>

		<c:choose>
			<c:when test="${phase == '1' }">
				<form method="post"
					action="GestionConseiller?action=fromEffectuervirementphase1">
					<fieldset id="section-1">
						<legend> Sélection du compte à débiter </legend>
						<label for="motcle1">chercher un client :</label><input
							type="text" name="motcle1" id="motcle1"
							value='<c:out value=""></c:out>' /><br /> <br />

					</fieldset>
					<fieldset id="section-2">
						<legend> Sélection du compte à créditer </legend>
						<label for="motcle2">chercher un client :</label><input
							type="text" name="motcle2" id="motcle2"
							value='<c:out value=""></c:out>' /><br />

					</fieldset>
					<br /> <input type="submit" name="validerChercherClient"
						value="suivant" /><br />
				</form>
			</c:when>
			<c:when test="${phase == '2' }">
			<form method="post"
					action="GestionConseiller?action=fromEffectuervirementphase2">
				<table class="table table-bordered table-striped table-condensed">
					<tr>
						<th>&nbsp;</th>
						<th>numéro de compte</th>
						<th>type de Compte</th>
						<th>date d'ouverture</th>
						<th>solde</th>

					</tr>
					<c:forEach var="compte" items="${listecomptesclientAdebiter}">
						<tr>
							<td><input type="radio" name="idcompte1form"
								id="idcompte1form"
								value="<c:out value="${compte.idCompte}"></</c:out>" /></td>
							<td><c:out value="${compte.numeroCompte}"></</c:out></td>
							<td><c:out value="${compte.typeCompte}"></</c:out></td>
							<td><c:out value="${compte.dateOuverture}"></</c:out></td>
							<td><c:out value="${compte.solde}"></</c:out></td>

						</tr>
					</c:forEach>
				</table>
				<table class="table table-bordered table-striped table-condensed">
					<tr>
						<th>&nbsp;</th>
						<th>numéro de compte</th>
						<th>type de Compte</th>
						<th>date d'ouverture</th>
						<th>solde</th>

					</tr>
					<c:forEach var="compte2" items="${listecomptesclientAcrediter}">
						<tr>
							<td><input type="radio" name="idcompte2form"
								id="idcompte2form"
								value="<c:out value="${compte2.idCompte}"></</c:out>" /></td>
							<td><c:out value="${compte2.numeroCompte}"></</c:out></td>
							<td><c:out value="${compte2.typeCompte}"></</c:out></td>
							<td><c:out value="${compte2.dateOuverture}"></</c:out></td>
							<td><c:out value="${compte2.solde}"></</c:out></td>

						</tr>
					</c:forEach>
				</table>
				<br /> <input type="submit" name="validerComptesClients" value="suivant"/>
				</form>
			</c:when>
			<c:when test="${phase == '3' }">
				<form method="post"
					action="GestionConseiller?action=fromEffectuervirementphase3">
					<fieldset id="section-1">
						<legend> Sélection du montant à transférer </legend>
						<label for="montant">saisir un montant :</label><input
							type="text" name="montant" id="montant"
							value='<c:out value=""></c:out>' /><br /> <br />

					</fieldset>

					<br /> <input type="submit" name="validerMontant"
						value="valider" /><br />
				</form>
			
			</c:when>
		</c:choose>
	</div>
	</section>
	<section class="row">

	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<br /> <a href="GestionConseiller?action=interfaceConseiller">interface
			Conseiller</a> <br /> <a href="index.html">retour accueil</a>
	</div>
	</section>
</body>
</html>