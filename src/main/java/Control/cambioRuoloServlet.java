package Control;

import Model.utente.utenteBean;
import Model.utente.utenteDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cambioRuoloServlet")

public class cambioRuoloServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long userId = Long.parseLong(request.getParameter("userId"));
        boolean makeAdmin = Boolean.parseBoolean(request.getParameter("makeAdmin"));

        try (utenteDAO utenteDAO = new utenteDAO()) {
            utenteBean utente = utenteDAO.doRetrieveByKey(userId);
            if (utente != null) {
                utente.setIsAdmin(makeAdmin);
                utenteDAO.doUpdate(utente);
            }
        } catch (SQLException e) {
            throw new ServletException("Errore durante il cambio del ruolo dell'utente", e);
        }

        response.sendRedirect("profiloAdmin.jsp");
    }
}
