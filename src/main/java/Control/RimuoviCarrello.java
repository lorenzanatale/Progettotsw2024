package Control;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import Model.prodottoCarrello.prodottoCarrelloBean;
import Model.prodottoCarrello.prodottoCarrelloDAO;

@WebServlet(name = "RimuoviDalCarrelloServlet", value = "/rimuovi-dal-carrello-servlet")
public class RimuoviCarrello extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (prodottoCarrelloDAO dao = new prodottoCarrelloDAO()) {

            HttpSession session = request.getSession();

            long productId = Long.parseLong(request.getParameter("productId"));
            Long cartId = (Long) session.getAttribute("cartId");
            int quantityToRemove = Integer.parseInt(request.getParameter("quantity"));

            if (session.getAttribute("userId") == null) {
                // Gestisci carrello anonimo
                CarrelloAnonimo carrelloAnonimo = (CarrelloAnonimo) session.getAttribute("cartAnonimo");

                if (carrelloAnonimo != null) {
                    prodottoCarrelloBean prodottoCarrello = new prodottoCarrelloBean();
                    prodottoCarrello.setIdProdotto(productId);
                    prodottoCarrello.setQuantita(quantityToRemove);

                    // Rimuovi il prodotto dal carrello anonimo
                    carrelloAnonimo.removeProduct(prodottoCarrello);
                    session.setAttribute("cartAnonimo", carrelloAnonimo);
                }

            } else {
                // Gestisci carrello dell'utente loggato
                dao.removeProduct(productId, cartId, quantityToRemove);
            }

            // Redirect o forward a una pagina appropriata
            response.sendRedirect("carrello.jsp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
