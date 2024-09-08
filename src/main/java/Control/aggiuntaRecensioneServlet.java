package Control;

import tswProj.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Model.recensione.*;
import Model.utente.utenteBean;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

@WebServlet("/aggiuntaRecensioneServlet")

public class aggiuntaRecensioneServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		utenteBean user = (utenteBean) session.getAttribute("user");
		
	    long idUtente = user != null ? user.getId() : 0;
	    long idProdotto = Long.parseLong(request.getParameter("idProdotto"));
	    String titolo = request.getParameter("titolo");
	    String commento = request.getParameter("commento");
	    double rating = Double.parseDouble(request.getParameter("valutazione"));

	    recensioneBean recensione = new recensioneBean();
	    recensione.setIdUtente(idUtente);
	    recensione.setIdProdotto(idProdotto);
	    recensione.setTitolo(titolo);
	    recensione.setCommento(commento);
	    recensione.setValutazione(rating);

	    LocalDate currentDate = LocalDate.now();
	    recensione.setData(Date.valueOf(currentDate));

	    try (recensioneDAO recensioneDAO = new recensioneDAO()) {
	        recensioneDAO.doSave(recensione);
	    } catch (SQLException | EmptyPoolException e) {
	    	throw new ServletException(e);
	    }

	    response.sendRedirect(request.getContextPath() + "/prodottoServlet?id=" + idProdotto);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}