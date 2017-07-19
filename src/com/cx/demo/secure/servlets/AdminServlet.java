package com.cx.demo.secure.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cx.demo.secure.commons.*;

public class AdminServlet extends HttpServlet {

	private static final long serialVersionUID = 7890L;

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

			String dbUser     = req.getParameter("dbUser");
			String dbPassword = req.getParameter("dbPassword");

			dal.setDbParams(dbUser, dbPassword);

			getServletConfig().getServletContext().getRequestDispatcher("/admin.jsp").forward(req,resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
