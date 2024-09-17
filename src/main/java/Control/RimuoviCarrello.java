
package Control;

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

@WebServlet("/RimuovidalcarrelloServlet")
public class RimuoviCarrello extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long prodottoId;

        // Leggi parametri dalla richiesta
        try {
            prodottoId = Long.parseLong(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            log("Invalid productId input: " + request.getParameter("productId"), e);
            response.sendRedirect("error.jsp?message=Invalid product ID");
            return;
        }

        HttpSession session = request.getSession();
        Long carrelloId = (Long) session.getAttribute("carrelloId");

        if (carrelloId == null) {
            log("Carrello non trovato per l'utente.");
            response.sendRedirect("error.jsp?message=Cart not found");
            return;
        }

        try (carrelloDAO carrelloDAO = new carrelloDAO();
             prodottoCarrelloDAO prodottoCarrelloDAO = new prodottoCarrelloDAO()) {
            // Rimuovi il prodotto dal carrello
            prodottoCarrelloDAO.removeProduct(prodottoId, carrelloId);
            log("Prodotto rimosso dal carrello. ID prodotto: " + prodottoId);

            // Recupera gli articoli rimanenti nel carrello
            List<prodottoCarrelloBean> prodotti = prodottoCarrelloDAO.doRetrieveByCarrelloId(carrelloId);
            System.out.println(prodotti);
            if (prodotti.isEmpty()) {
                session.setAttribute("emptyCartMessage", "Il tuo carrello è vuoto.");
            } else {
                session.setAttribute("prodotti", prodotti);
                session.removeAttribute("emptyCartMessage");
            }

            // Reindirizza alla pagina del carrello
            request.getRequestDispatcher("carrello.jsp").forward(request, response);

        } catch (SQLException e){
            log("SQL error during product removal or cart retrieval", e);
            response.sendRedirect("error.jsp?message=Database error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.getRequestDispatcher("carrello.jsp").forward(request, response);
        } catch (Exception e) {
            log("Error forwarding to carrello.jsp", e);
            response.sendRedirect("error.jsp?message=Unable to load cart");
        }
    }
}
