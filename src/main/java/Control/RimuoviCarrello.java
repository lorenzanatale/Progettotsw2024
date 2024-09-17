package Control;

import Model.carrello.carrelloBean;
import Model.carrello.carrelloDAO;
import Model.prodottoCarrello.prodottoCarrelloBean;
import Model.prodottoCarrello.prodottoCarrelloDAO;
import tswProj.EmptyPoolException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/RimuoviCarrelloServlet")
public class RimuoviCarrello extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long prodotto;
        int quantita;

        // Leggi parametri dalla richiesta
        try {
            prodotto = Long.parseLong(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp?message=Invalid input");
            return;
        }

        HttpSession session = request.getSession();
        Long carrelloId = (Long) session.getAttribute("carrelloId");
        Long userId = (Long) session.getAttribute("userId");

        try (carrelloDAO carrelloDAO = new carrelloDAO();
             prodottoCarrelloDAO prodottoCarrelloDAO = new prodottoCarrelloDAO()) {

            if (userId != null) {
                // Utente loggato: recupera o crea il carrello
                if (carrelloId == null) {
                    carrelloId = carrelloDAO.doRetrieveByUserId(userId);
                    session.setAttribute("carrelloId", carrelloId);
                }
            } else {
                // Utente anonimo: gestisci carrello senza cookie
                if (carrelloId == null) {
                    carrelloBean carrello = new carrelloBean();
                    carrello.setIdUtente(0); // Indica utente anonimo
                    carrelloId = carrelloDAO.doSave(carrello);
                    session.setAttribute("carrelloId", carrelloId);
                }
            }

            // Rimuovi il prodotto dal carrello
            prodottoCarrelloDAO.removeProduct(prodotto, carrelloId);

            // Recupera gli articoli del carrello per visualizzazione
            List<prodottoCarrelloBean> prodotti = prodottoCarrelloDAO.doRetrieveByCarrelloId(carrelloId);
            if (prodotti.isEmpty()) {
                session.setAttribute("emptyCartMessage", "Il tuo carrello è vuoto.");
                session.removeAttribute("prodotti");
            } else {
                session.setAttribute("prodotti", prodotti);
                session.removeAttribute("emptyCartMessage"); // Rimuovi il messaggio se il carrello non è vuoto
            }

            // Forward alla JSP per visualizzare il carrello aggiornato
            request.getRequestDispatcher("carrello.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error managing cart", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect alla JSP di visualizzazione del carrello
        request.getRequestDispatcher("carrello.jsp").forward(request, response);
    }
}
