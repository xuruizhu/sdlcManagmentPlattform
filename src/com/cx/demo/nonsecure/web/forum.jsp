<%@ page import="java.util.*" %>
<%@ page import="com.cx.demo.nonsecure.commons.*" %>
<%
	CxDemoUser user = (CxDemoUser) session.getAttribute("user");
	CxDemoProperties props = CxDemoProperties.getInstance(getServletContext().getRealPath("/"));
	CxDemoDAL dal = CxDemoDAL.getInstance(props);
%>
<html lang='<%= props.getProperty(CxDemoProperties.KEY_APP_LANG) %>'> 
	<head>
		<meta http-equiv='Content-Type' content='text/html; charset=<%= props.getProperty(CxDemoProperties.KEY_APP_ENCODE) %>' />
		<link rel='stylesheet' href='<%= props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "/demo.css" %>'>
		<title><%= props.getLabel("JSP_TITLE_FORUM") %></title>
	</head>
	<body background='<%= props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "/background.jpg" %>'  onLoad='document.posting.post.focus()'>
		<h1><%= props.getLabel("JSP_WELCOME_FORUM") + " (" + user.toString()  +")"%></h1>
		<h2></h2>
		<form method='post' action='/demo-nonsecure/forum.do' name='posting'>
			<table>
				<tr>
					<td colspan='2'><%= props.getLabel("POST") %>: </td>
					<td colspan='3'><input class='field' type='text' name='post'></td>
				</tr>
				<tr>
					<td colspan='2'></td>
					<td colspan='3' align='right'>
						<input class='button' type='submit' name='submit-post'  value='<%= props.getLabel("BUTTON_POST") %>'>
						&nbsp;&nbsp;
						<input class='button' type='submit' name='submit-reset' value='<%= props.getLabel("BUTTON_RESET_POSTS") %>'>
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
		<h2><%= props.getLabel("LAST_POST") %></h2>
		<form name='lastPost'>
			<h2 class='post-last'>
<%
	String latestPost = request.getParameter("post");
	if (latestPost != null) {
		out.println(latestPost);
	}
%>
			</h2>
		</form>
		<h2><%= props.getLabel("TITLE_POSTS") %></h2>
		<form class='posts' name='prevPosts'>
<%
	ArrayList<CxDemoPost> posts = dal.getLatestPosts(30);
	for (int i=0; i<posts.size(); i++) {
		CxDemoPost post = posts.get(i);
		out.println("<h2 class='post'><u>" + post.getPublisher() + "&nbsp;&nbsp;[" + post.getPostTimeString() + "]</u>:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + post.getText() + "</h2>");
	}
%>		
		</form>
		<form class='link'>
			<table>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td align='right'><a href='/demo-nonsecure/login.jsp'><%= props.getLabel("LINK_LOGOUT") %></a></td>
				</tr>
			</table>
		</form>
	</body>     
</html>     