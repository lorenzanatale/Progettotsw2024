package Control;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Model.prodotto.prodottoBean;
import Model.prodotto.prodottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/modificaProdottoServlet")

public class modificaProdottoServlet extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String categoria = request.getParameter("categoria");

        try (prodottoDAO prodottoDAO = new prodottoDAO()) {
            prodottoBean prodotto = prodottoDAO.doRetrieveByKey(id);

            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setCategoria(categoria);

            prodottoDAO.doUpdate(prodotto);
            request.setAttribute("prodotto", prodotto);

        } catch (SQLException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("prodottoServlet?id=" + id);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}