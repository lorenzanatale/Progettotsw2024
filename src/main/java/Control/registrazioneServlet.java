package Control;

import tswProj.EmailAlreadyUsedException;
import tswProj.EmptyPoolException;
import tswProj.Sicurezza;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Model.utente.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@WebServlet("/registrazioneServlet")
public class registrazioneServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		if (!password.equals(confirmPassword)) {
			response.sendRedirect("registrazione.jsp?error=error");
			return;
		}

		try {
			if (utenteRegistrato(username, email, password)) {
				response.sendRedirect("login.jsp");
			} else {
				request.setAttribute("errorMessage", "Registrazione non riuscita. Riprova.");
				request.getRequestDispatcher("/registrazione.jsp").forward(request, response);
			}
		} catch (EmailAlreadyUsedException e) {
			HttpSession session = request.getSession();
			session.setAttribute("emailExists", true);
			response.sendRedirect("registrazione.jsp");
		}
	}
    
	private boolean utenteRegistrato(String username, String email, String password) throws EmailAlreadyUsedException {
		utenteBean utente = new utenteBean();

		Optional<String> hashedPassword = Sicurezza.hashPassword(password);
		if (hashedPassword.isEmpty()) {
			System.out.println("Password non valida");
			return false;
		}

		utente.setUsername(username);
		utente.setEmail(email);
		utente.setPassword(hashedPassword.get());
		utente.setIsAdmin(false);

		try (utenteDAO user = new utenteDAO()) {
			long userId = user.doSave(utente);
			System.out.println("Utente salvato con successo con ID: " + userId);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
