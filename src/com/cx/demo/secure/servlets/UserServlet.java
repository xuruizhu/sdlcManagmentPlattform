package com.cx.demo.secure.servlets;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cx.demo.secure.commons.*;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 7890L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		try {
			doPost(req, resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			CxDemoProperties props = CxDemoProperties.getInstance(getServletContext().getRealPath("/"));
			CxDemoDAL dal = CxDemoDAL.getInstance(props);

			HttpSession session = req.getSession(false);

			CxDemoUser user = (CxDemoUser) session.getAttribute("user");

			String currentPass = req.getParameter("currentPass");
			String newPass = req.getParameter("newPass");
			String confirmPass = req.getParameter("confirmPass");


			if (newPass.equals(confirmPass)) {
				String password = dal.getPassword(user.getId());
				boolean authenticated = password != null && dal.authenticateUser(user.getId(), currentPass);
				if (authenticated) {
					// Current password validated
					dal.setPassword(user.getId(), password, newPass);
				}
				else {
					// Authentication failure
					req.setAttribute("error", props.getLabel("ERROR_AUTH_FAILED"));
				}
			}
			else {
				// Confirmation does not match new password
				req.setAttribute("error", props.getLabel("ERROR_CONFIRM_FAILED"));
			}
			getServletConfig().getServletContext().getRequestDispatcher("/user.jsp").forward(req,resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
