package Control;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Model.recensione.recensioneDAO;

@WebServlet("/cancellaRecensioneServlet")

public class cancellaRecensioneServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long idRecensione = Integer.parseInt(request.getParameter("idRecensione"));
        long idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

        try (recensioneDAO recensioneDAO = new recensioneDAO()) {
            recensioneDAO.doDelete(idRecensione);
        } catch (Exception e) {
            throw new ServletException(e);
        }

        response.sendRedirect(request.getContextPath() + "/prodottoServlet?id=" + idProdotto);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}