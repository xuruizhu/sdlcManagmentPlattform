<%@ page import="java.util.*" %>
<%@ page import="com.cx.demo.nonsecure.commons.*" %>
<%
	CxDemoUser user = (CxDemoUser) session.getAttribute("user");
	CxDemoProperties props = CxDemoProperties.getInstance(getServletContext().getRealPath("/"));
%>
<html lang='<%= props.getProperty(CxDemoProperties.KEY_APP_LANG) %>'> 
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=<%= props.getProperty(CxDemoProperties.KEY_APP_ENCODE) %>' />
		<link rel='stylesheet' href='<%= props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "/demo.css" %>'>
		<title><%= props.getLabel("JSP_TITLE_AGENT") %></title>
	</head>
	<body background='<%= props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "/background.jpg" %>' onLoad='document.password.currentPass.focus()'>
		<h1><%= props.getLabel("JSP_WELCOME_AGENT") + " " + user.getId() %></h1>
		<h2><%= props.getLabel("TITLE_PERSONAL_DETAILS") %></h2>
		<form name='details'>
			<table>
				<tr>
					<td colspan='2'><%= props.getLabel("FULL_NAME") %>: </td>
					<td colspan='3'><%= user.toString() %></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("COUNTRY") %>: </td>
					<td colspan='3'><%= user.getCountry() %></td>
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
		<h2><%= props.getLabel("TITLE_CHANGE_PASS") %></h2>
		<form method='post' action='/demo-nonsecure/user.do' name='password'>
			<table>
				<tr>
					<td colspan='2'><%= props.getLabel("CURR_PASS") %>: </td>
					<td colspan='3'><input class='field' type='password' name='currentPass'></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("NEW_PASS") %>: </td>
					<td colspan='3'><input class='field' type='password' name='newPass'></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("CONF_PASS") %>: </td>
					<td colspan='3'><input class='field' type='password' name='confirmPass'></td>
				</tr>
				<tr>
					<td colspan='2'></td>
					<td colspan='3' align='right'><input class='button' type='submit' value='<%= props.getLabel("BUTTON_CHANGE") %>' ></td>
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
		out.println("<h2 class='error'>" + props.getLabel("ERROR_PASSWORD_CHANGE") + "&nbsp;&nbsp;&nbsp;&nbsp;" + error + "</h2>");
	}
%>
		<form class='link'>
			<table>
				<tr>
					<td><a href='/demo-nonsecure/forum.jsp'><%= props.getLabel("LINK_FORUM") %></a></td>
					<td></td>
					<td></td>
					<td></td>
					<td align='right'><a href='/demo-nonsecure/login.jsp'><%= props.getLabel("LINK_LOGOUT") %></a></td>
				</tr>
			</table>
		</form>
	</body>     
</html>     