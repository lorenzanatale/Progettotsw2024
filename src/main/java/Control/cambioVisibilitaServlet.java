package Control;


import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import Model.prodotto.prodottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cambioVisibilitaServlet")

public class cambioVisibilitaServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        boolean isVisible = Boolean.parseBoolean(request.getParameter("isVisible"));

        try (prodottoDAO prodottoDAO = new prodottoDAO()) {
            prodottoDAO.changeVisibility(productId, isVisible);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}