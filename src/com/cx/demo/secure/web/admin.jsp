<%@ page import="java.util.*" %>
<%@ page import="com.cx.demo.secure.commons.*" %>
<%
	CxDemoUser user = (CxDemoUser) session.getAttribute("user");
	CxDemoProperties props = CxDemoProperties.getInstance(getServletContext().getRealPath("/"));
	CxDemoDAL dal = CxDemoDAL.getInstance(props);
%>
<html lang='<%= props.getProperty(CxDemoProperties.KEY_APP_LANG) %>'> 
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=<%= props.getProperty(CxDemoProperties.KEY_APP_ENCODE) %>' />
		<link rel='stylesheet' href='<%= props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "/demo.css" %>'>
		<title><%= props.getLabel("JSP_TITLE_ADMIN") %></title>
	</head>
	<body background='<%= props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "/background.jpg" %>' onLoad='document.settings.dbDriver.focus()'>
		<h1><%= props.getLabel("JSP_WELCOME_ADMIN")%></h1>
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
		<h2><%= props.getLabel("TITLE_SYSTEM_SETTINGS") %></h2>
		<form method='post' action='/demo-secure/admin.do' name='settings'>
			<table>
				<tr>
					<td colspan='2'><%= props.getLabel("DB_USER") %>: </td>
					<td colspan='3'><input class='field' type='text' name='dbUser' value='<%= dal.getUser() %>'></td>
				</tr>
				<tr>
					<td colspan='2'><%= props.getLabel("DB_PASSWORD") %>: </td>
					<td colspan='3'><input class='field' type='password' name='dbPassword'></td>
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
		String error = CxDemoPost.encodeHtml(request.getAttribute("error").toString());
		out.println("<h2 class='error'>" + props.getLabel("ERROR_SETTING_CHANGE") + "&nbsp;&nbsp;&nbsp;&nbsp;" + error + "</h2>");
	}
	else {
		out.println("<br>");
	}
%>
		<br>
		<form class='link'>
			<table>
				<tr>
					<td><a href='/demo-secure/forum.jsp'><%= props.getLabel("LINK_FORUM") %></a></td>
					<td></td>
					<td></td>
					<td></td>
					<td align='right'><a href='/demo-secure/login.jsp'><%= props.getLabel("LINK_LOGOUT") %></a></td>
				</tr>
			</table>
		</form>
	</BODY>     
</HTML>     
