<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page" version="2.0"
	xmlns:tiles="http://tiles.apache.org/tags-tiles"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt">
	<fmt:setBundle basename="localization.Messages" />
	<c:choose>
		<c:when test="${not empty pageContext.request.userPrincipal}">
			<div>
				You are currently authenticated.
			</div>
		</c:when>
		<c:otherwise>
			<c:if test="${not empty param.login_error}">
				<div class="error">
					<fmt:message key="login.error" />
				</div>
			</c:if>
			<c:if test="${not empty param.login_timeout}">
				<div class="error">
					<fmt:message key="login.timeout" />
				</div>
			</c:if>
			<c:if test="${not empty param.login_max_session_exceed}">
				<div class="error">
					<fmt:message key="login.max_session_exceed" />
				</div>
			</c:if>
			<form name="f"
				action="${pageContext.request.contextPath}/j_spring_security_check"
				method="POST">
				<div class="loginbox">
					<div>
						<label for="j_username"><fmt:message key="login.username" /></label>
					</div>
					<div>
						<input id="j_username" type="text" name="j_username"
							value="${SPRING_SECURITY_LAST_USERNAME}" autofocus="autofocus"/>
					</div>
					<div>
						<label for="j_password"><fmt:message key="login.password" /></label>
					</div>
					<div>
						<input id="j_password" type="password" name='j_password' />
					</div>
					<div>
						<input name="submit" type="submit" id="submit" /><input
							id="_spring_security_remember_me" type="checkbox"
							name="_spring_security_remember_me" /> <label
							for="_spring_security_remember_me"><fmt:message
								key="login.rememberme" /></label>
					</div>
				</div>
			</form>
		</c:otherwise>
	</c:choose>
</jsp:root>

