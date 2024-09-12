package Control;

import tswProj.RuntimeSQLException;
import tswProj.EmptyPoolException;
import tswProj.Sicurezza;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Model.utente.utenteBean;
import Model.utente.utenteDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/profiloUtenteServlet")

public class profiloUtenteServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String newUsername = request.getParameter("newUsername");
		String newEmail = request.getParameter("newEmail");

	    HttpSession session = request.getSession();
	    if (session == null || session.getAttribute("id") == null) {
	        response.sendRedirect("login.jsp");
	        return;
	    }
	
	    long userId = (long) session.getAttribute("id");
	
	    try (utenteDAO userDAO = new utenteDAO()) {
	        utenteBean user = userDAO.doRetrieveByKey(userId);
	        
	        if ("editEmail".equals(action)) {
                if (!Sicurezza.emailValida(newEmail)) {
                    response.getWriter().write("invalid");
                    return;
                } else {
                    user.setEmail(newEmail);
                    userDAO.doUpdate(user);
                }
            } else if ("editUsername".equals(action)) {
                if (!Sicurezza.usernameValido(newUsername)) {
                	response.getWriter().write("invalid");
                    return;
                } else {
                    user.setUsername(newUsername);
                    userDAO.doUpdate(user);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Errore durante l'aggiornamento delle informazioni dell'utente", e);
        }
	    
	    response.getWriter().write("valid");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}