package Control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Model.prodotto.prodottoBean;
import Model.prodotto.prodottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/prodottoServlet")

public class prodottoServlet extends HttpServlet {
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("id") == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        long productId = Long.parseLong(request.getParameter("id"));
        try(prodottoDAO prodottoDAO = new prodottoDAO()) {
            prodottoBean prodotto = prodottoDAO.doRetrieveByKey(productId);
            request.setAttribute("prodotto", prodotto);
            request.getRequestDispatcher("/prodotto.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}