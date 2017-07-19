<%@ page import="com.cx.demo.nonsecure.commons.*" %>
<%
	CxDemoUser user = (CxDemoUser) session.getAttribute("user");
	CxDemoProperties props = CxDemoProperties.getInstance(getServletContext().getRealPath("/"));
%>
<html lang='<%= props.getProperty(CxDemoProperties.KEY_APP_LANG) %>'> 
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=<%= props.getProperty(CxDemoProperties.KEY_APP_ENCODE) %>' />
		<link rel='stylesheet' href='<%= props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "/demo.css" %>'>
		<title><%= props.getLabel("JSP_TITLE_LOGIN") %></title>
	</head>
	<body background='<%= props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "/background.jpg" %>'  onLoad='document.login.id.focus()'>
		<h1><%= props.getLabel("JSP_WELCOME_LOGIN") %></h1>
		<br>
		<h2><%= props.getLabel("TITLE_LOGIN") %></h2>
		<form method='post' action='/demo-nonsecure/login.do' name='login'>
			<table>
				<tr>
					<td colspan='2'><%= props.getLabel("ID") %>: </td>
					<td colspan='3'><input class='field' type='text' name='id'></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("PASSWORD") %>: </td>
					<td colspan='3'><input class='field' type='password' name='password'></td>
				</tr>
				<tr>
					<td colspan='2'></td>
					<td colspan='3' align='right'>
						<input class='button' type='submit' name='submit-login' value='<%= props.getLabel("BUTTON_LOGIN") %>'>
						&nbsp;&nbsp;
						<input class='button' type='submit' name='submit-reset' value='<%= props.getLabel("BUTTON_RESET_USERS") %>'>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</form>
		<h2><%= props.getLabel("TITLE_REGISTER") %></h2>
		<form method='post' action='/demo-nonsecure/register.do' name='register'>
			<table>
				<tr>
					<td colspan='2'><%= props.getLabel("ID") %>: </td>
					<td colspan='3'><input class='field' type='text' name='id'></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("FIRST_NAME") %>: </td>
					<td colspan='3'><input class='field' type='text' name='name'></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("LAST_NAME") %>: </td>
					<td colspan='3'><input class='field' type='text' name='surname'></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("COUNTRY") %>: </td>
					<td colspan='3'><input class='field' type='text' name='country'></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("PASSWORD") %>: </td>
					<td colspan='3'><input class='field' type='password' name='password'></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("PASSWORD_CONFIRM") %>: </td>
					<td colspan='3'><input class='field' type='password' name='password2'></td>
				</tr>
				<tr>
					<td colspan='2'></td>
					<td colspan='3' align='right'>
						<input class='button' type='submit' name='register' value='<%= props.getLabel("BUTTON_REGISTER") %>'>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</form>
<% 
	if (request.getAttribute("error") != null) {
		String error = request.getAttribute("error").toString();
		out.println("<h2 class='error'>" + props.getLabel("ERROR_OPERATION") + "&nbsp;&nbsp;&nbsp;&nbsp;" + error + "</h2>");
	}
	else {
		out.println("<br>");
	}
%>
	</body>     
</html>     