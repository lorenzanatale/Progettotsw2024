package Control;

import java.io.IOException;
import java.util.List;

import tswProj.EmptyPoolException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import Model.prodotto.*;

import javax.swing.*;

@WebServlet("/catalogoServlet")

public class catalogoServlet extends javax.servlet.http.HttpServlet {
	
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
	        try (prodottoDAO prodottoDAO = new prodottoDAO()) {
	            List<prodottoBean> prodotti;
	            prodotti = (List<prodottoBean>) prodottoDAO.doRetrieveAll("ASC");
	            request.setAttribute("prodotti", prodotti);
	            request.getRequestDispatcher("/catalogo.jsp").forward(request, response);
	            } catch (Exception e) {
	                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
	              }
	        }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	    }
}