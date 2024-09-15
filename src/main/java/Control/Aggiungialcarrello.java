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

@WebServlet("/AggiungialcarrelloServlet")
public class Aggiungialcarrello extends HttpServlet {

    private carrelloDAO carrelloDAO;
    private prodottoCarrelloDAO prodottoCarrelloDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            carrelloDAO = new carrelloDAO();
            prodottoCarrelloDAO = new prodottoCarrelloDAO();
        } catch (EmptyPoolException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long prodottoId;
        int quantita;

        // Leggi parametri dalla richiesta
        try {
            prodottoId = Long.parseLong(request.getParameter("productId"));
            quantita = Integer.parseInt(request.getParameter("quantity"));
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp?message=Invalid input");
            return;
        }

        HttpSession session = request.getSession();
        Long carrelloId = (Long) session.getAttribute("carrelloId");
        Long userId = (Long) session.getAttribute("userId");

        try {
            if (userId != null) {
                // Utente loggato: recupera o crea il carrello
                if (carrelloId == null) {
                    carrelloId = carrelloDAO.doRetrieveByUserId(userId);
                    if (carrelloId == null) {
                        carrelloBean carrello = new carrelloBean();
                        carrello.setIdUtente(userId);
                        carrelloId = carrelloDAO.doSave(carrello);
                    }
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

            // Aggiungi il prodotto al carrello
            prodottoCarrelloDAO.addProduct(prodottoId, carrelloId, quantita, "");

        } catch (SQLException e) {
            throw new ServletException("Error managing cart", e);
        }

        response.sendRedirect("carrello.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long carrelloId = (Long) session.getAttribute("carrelloId");
        Long userId = (Long) session.getAttribute("userId");

        if (carrelloId == null) {
            response.sendRedirect("error.jsp?message=Carrello non trovato o sessione scaduta. Accedi per visualizzare il tuo carrello.");
            return;
        }

        try {
            List<prodottoCarrelloBean> prodotti = prodottoCarrelloDAO.getCartItemsAsProducts(carrelloId);
            request.setAttribute("prodotti", prodotti);
            request.getRequestDispatcher("carrello.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error retrieving cart items", e);
        }
    }
}
