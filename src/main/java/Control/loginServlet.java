package Control;

import Model.utente.utenteBean;
import Model.utente.utenteDAO;
import tswProj.Sicurezza;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		if (email == null || password == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp?error=error");
			return;
		}

		utenteBean user;
		try (utenteDAO utenteDAO = new utenteDAO()) {
			user = utenteDAO.doRetrieveByEmail(email);
		} catch (SQLException e) {
			throw new RuntimeException("Errore durante il recupero dell'utente", e);
		}

		if (user == null || !checkLogin(user, password)) {
			response.sendRedirect(request.getContextPath() + "/login.jsp?error=error");
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		session.setAttribute("id", user.getId());
		session.setAttribute("isAdmin", user.getIsAdmin());

		// Visualizza gli articoli del carrello dell'utente, se presenti
		session.setAttribute("userCartId", user.getId()); // Presumendo che il carrello dell'utente sia identificato dal suo ID

		response.sendRedirect(request.getContextPath() + "/home.jsp");
	}

	private boolean checkLogin(utenteBean user, String password) {
		return Sicurezza.verifyPassword(password, user.getPassword());
	}
}
