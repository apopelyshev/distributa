<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<script src="../js/bootstrap.min.js"></script>
</head>
<body>
	<c:choose>
		<c:when test="${not empty members}">
			<c:forEach var="member" items="${members.getArr()}">
				<div>
					<img src="${member.getImagePath()}" />
					<p>${member.getName()}, Active: ${member.checkActive()}</p>
				</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
</body>