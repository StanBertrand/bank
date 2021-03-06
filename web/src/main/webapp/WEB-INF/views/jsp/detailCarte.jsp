<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt">
	<fmt:setBundle basename="localization.Messages" />
	<h2>Détails carte</h2>
	<table>
		<tr>
			<th>Nom du compte</th>
			<td>${compte.compteId}</td>
		</tr>
		<tr>
			<th>Nom de la carte</th>
			<td>${compte.numCarte}</td>
		</tr>
	</table>

	<c:url value="/secure/account/${month}/${compte.compteId}.html"
		var="retourCompte" />

	<p>
		<a href="${retourCompte }"><fmt:message key="carte.retour" /></a>
	</p>

	<h2>Opérations</h2>
	<div class="buttonRow">
		<c:forEach var="entry" items="${months}" varStatus="loopStatusUrl">
			<c:url
				value="/secure/detailCarte/${entry.key}/${compte.compteId}.html"
				var="carteURL" />
			<a href="${carteURL }"
				class="button${month == entry.key ? ' selected' :'' }"> <fmt:formatDate
					value="${entry.value}" pattern="MMM yyyy" />
			</a>
		</c:forEach>

	</div>
	<table>
		<tr>
			<th>Date</th>
			<th>Libellé</th>
			<th class="numeric">Montant</th>
		</tr>

		<c:forEach var="operation" items="${operations}"
			varStatus="loopStatus">
			<tr class="${loopStatus.index % 2 == 0 ? 'odd' : 'even'}">
				<td><fmt:formatDate value="${operation.transaction.dateValid.toDate()}" /></td>
				<td>${operation.transaction.libelle}</td>
				<td
					class="numeric ${operation.operationType=='DEBIT' ? 'negative' : 'positive'}"><fmt:formatNumber
						type="currency" currencyCode="EUR"
						value="${operation.operationType=='DEBIT' ? - operation.montant / 100.0 : operation.montant / 100.0}" /></td>
			</tr>
		</c:forEach>

	</table>
</jsp:root>
