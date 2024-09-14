package Control;

import Model.carrello.carrelloDAO;
import Model.prodottoCarrello.prodottoCarrelloBean;
import Model.prodottoCarrello.prodottoCarrelloDAO;
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
import java.util.ArrayList;
import java.util.List;

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

		try (carrelloDAO carrelloDAO = new carrelloDAO();
			 prodottoCarrelloDAO prodottoCarrelloDAO = new prodottoCarrelloDAO()) {

			// Recupera o crea il carrello dell'utente
			long userCartId = carrelloDAO.doRetriveByUserId(user.getId());
			session.setAttribute("userCartId", userCartId);

			// Recupera il carrello anonimo dalla sessione
			CarrelloAnonimo carrelloAnonimo = (CarrelloAnonimo) session.getAttribute("cartAnonimo");

			if (carrelloAnonimo != null) {
				// Unisci i carrelli
				mergeCarts(carrelloAnonimo, userCartId, prodottoCarrelloDAO);
				// Rimuovi il carrello anonimo dalla sessione
				session.removeAttribute("cartAnonimo");
			}
		} catch (SQLException e) {
			throw new RuntimeException("Errore durante la gestione dei carrelli", e);
		}

		response.sendRedirect(request.getContextPath() + "/home.jsp");
	}

	private boolean checkLogin(utenteBean user, String password) {
		return Sicurezza.verifyPassword(password, user.getPassword());
	}

	private void mergeCarts(CarrelloAnonimo carrelloAnonimo, long userCartId, prodottoCarrelloDAO prodottoCarrelloDAO) throws SQLException {
		// Crea una copia della lista per l'iterazione
		List<prodottoCarrelloBean> anonymousCartItems = new ArrayList<>(carrelloAnonimo.getListaCarrello());

		for (prodottoCarrelloBean item : anonymousCartItems) {
			prodottoCarrelloBean existingItem = prodottoCarrelloDAO.getProductFromCart(userCartId, item.getIdProdotto());

			if (existingItem != null) {
				// Aggiorna la quantità esistente
				int newQuantity = existingItem.getQuantita() + item.getQuantita();
				prodottoCarrelloDAO.updateProductQuantity(userCartId, item.getIdProdotto(), newQuantity);
			} else {
				// Aggiungi il prodotto al carrello dell'utente
				prodottoCarrelloDAO.addProduct(item.getIdProdotto(), userCartId, item.getQuantita(), item.getNome());
			}
		}

		// Ora rimuovi tutti i prodotti dal carrello anonimo
		for (prodottoCarrelloBean item : anonymousCartItems) {
			carrelloAnonimo.removeProduct(item);
		}
	}
}
