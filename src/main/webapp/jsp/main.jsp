<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<script src="../js/bootstrap.min.js"></script>
</head>
<body>
	<c:choose>
		<c:when test="${not empty peopleArr}">
			<c:forEach var="person" items="${peopleArr}">
				<div>
					<img src="${person.getImagePath()}" />
					<p>${person.getName()}</p>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</body>