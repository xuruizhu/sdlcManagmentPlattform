package com.cx.demo.secure.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cx.demo.secure.commons.*;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 7890L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			doPost(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			CxDemoProperties props = CxDemoProperties.getInstance(getServletContext().getRealPath("/"));
			CxDemoDAL dal = CxDemoDAL.getInstance(props);

			HttpSession session = req.getSession(true);

			if (req.getParameter("submit-reset") != null) {
				// Reset users table in database
				try {
					dal.resetUsers();
					getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(req,resp);
					return;
				}
				catch (CxDemoException ex) {
					// Authentication failed
					ex.printStackTrace();
					req.setAttribute("error", props.getLabel("ERROR_RESET_USERS"));
					getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(req,resp);
					return;
				}
			}
			else {
				// Handle login
				// Get request parameters and store the in the session
				String id = req.getParameter("id");
				String password = req.getParameter("password");
				boolean authenticated = password != null && dal.authenticateUser(id, password);
				if (authenticated) {
					// Authentication passed
					CxDemoUser user = dal.getUserInfo(id);
					session.setAttribute("user", user);
					if ("admin".equals(user.getId())) {
						getServletConfig().getServletContext().getRequestDispatcher("/admin.jsp").forward(req,resp);
						return;
					}
					else {
						getServletConfig().getServletContext().getRequestDispatcher("/user.jsp").forward(req,resp);
						return;
					}
				}
				else {
					// Authentication failed
					req.setAttribute("error", props.getLabel("ERROR_AUTH_FAILED"));
					getServletConfig().getServletContext().getRequestDispatcher("/login.jsp").forward(req,resp);
					return;
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
