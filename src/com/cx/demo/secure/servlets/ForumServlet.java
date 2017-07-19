package com.cx.demo.secure.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cx.demo.secure.commons.*;

public class ForumServlet extends HttpServlet {

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
			HttpSession session = req.getSession(false);
			CxDemoProperties props = CxDemoProperties.getInstance(getServletContext().getRealPath("/"));
			CxDemoDAL dal = CxDemoDAL.getInstance(props);

			if (req.getParameter("submit-reset") != null) {
				// Reset users table in database
				try {
					dal.resetPosts();
				}
				catch (CxDemoException ex) {
					ex.printStackTrace();
				}
			}
			else {
				String text = req.getParameter("post");
				if (text != null && text.length() > 0) {
					CxDemoUser publisher = (CxDemoUser) session.getAttribute("user");
					CxDemoPost post = new CxDemoPost(text, publisher.getId());
					dal.post(post);
				}
			}
			getServletConfig().getServletContext().getRequestDispatcher("/forum.jsp").forward(req,resp);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
