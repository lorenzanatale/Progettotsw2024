package Control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import Model.prodotto.prodottoBean;
import Model.prodotto.prodottoDAO;

@WebServlet("/searchServlet")

public class searchServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		if (query != null && !query.trim().isEmpty()) {
		try(prodottoDAO prodottoDAO = new prodottoDAO()) {
			
			List<prodottoBean> prodotti = prodottoDAO.doRetrieveByName(query);
			
			String json = new Gson().toJson(prodotti);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero dei dati");
		}
	} else {
		response.getWriter().write("[]");
	}
	}
}