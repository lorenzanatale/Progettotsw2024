package Control;

import Model.ordine.ordineBean;
import Model.ordine.ordineDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/storicoOrdiniServlet")

public class StoricoOrdini extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long userId = Long.parseLong(request.getParameter("userId"));

        try (ordineDAO ordineDAO = new ordineDAO()) {
            Collection<ordineBean> ordini = ordineDAO.doRetrieveByUser(userId);
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("storicoOrdini.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Errore durante il recupero dello storico ordini", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}