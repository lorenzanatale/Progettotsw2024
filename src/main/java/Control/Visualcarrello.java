package Control;

import Model.prodottoCarrello.prodottoCarrelloBean;
import Model.prodottoCarrello.prodottoCarrelloDAO;
import Model.carrello.carrelloBean;
import Model.carrello.carrelloDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "VisualcarrelloServlet", value = "/Visualcarrello-servlet")
public class Visualcarrello extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<prodottoCarrelloBean> prodottiNelCarrello;
        String messaggio = "";

        prodottoCarrelloDAO prodottoCarrelloDAO = null;
        carrelloDAO carrelloDAO = null;

        try {
            prodottoCarrelloDAO = new prodottoCarrelloDAO();
            carrelloDAO = new carrelloDAO();

            Long userId = (Long) session.getAttribute("userId");
            Long cartId = (Long) session.getAttribute("cartId");
            CarrelloAnonimo carrelloAnonimo = (CarrelloAnonimo) session.getAttribute("cartAnonimo");

            // Ottieni i prodotti dal carrello (anonimo o utente loggato)
            prodottiNelCarrello = recuperaProdottiCarrello(userId, cartId, carrelloAnonimo, carrelloDAO, prodottoCarrelloDAO);

            if (prodottiNelCarrello == null || prodottiNelCarrello.isEmpty()) {
                messaggio = "Il carrello è vuoto.";
            }

            // Imposta i prodotti e il messaggio come attributi della richiesta
            request.setAttribute("prodottiCarrello", prodottiNelCarrello);
            request.setAttribute("messaggio", messaggio);

            // Se l'utente è loggato, unisci i carrelli e rimuovi il carrello anonimo
            if (userId != null && carrelloAnonimo != null) {
                mergeCartWithUserCart(session, carrelloAnonimo, cartId, prodottoCarrelloDAO, carrelloDAO);
                session.removeAttribute("cartAnonimo"); // Rimuovi il carrello anonimo
            }

            // Inoltra la richiesta alla pagina JSP per la visualizzazione
            request.getRequestDispatcher("/carrello.jsp").forward(request, response);

        } catch (SQLException | ServletException e) {
            // Gestione degli errori
            request.setAttribute("errorMessage", "Errore durante la visualizzazione del carrello: " + e.getMessage());
            try {
                request.getRequestDispatcher("/errore.jsp").forward(request, response);
            } catch (ServletException | IOException ex) {
                throw new RuntimeException("Errore nella gestione della richiesta di errore", ex);
            }
        } finally {
            // Chiudi manualmente i DAO se non implementano AutoCloseable
            if (prodottoCarrelloDAO != null) {
                prodottoCarrelloDAO.close();
            }
            if (carrelloDAO != null) {
                carrelloDAO.close();
            }
        }
    }

    // Funzione che recupera i prodotti dal carrello anonimo o utente loggato
    private List<prodottoCarrelloBean> recuperaProdottiCarrello(Long userId, Long cartId, CarrelloAnonimo carrelloAnonimo, carrelloDAO carrelloDAO, prodottoCarrelloDAO prodottoCarrelloDAO) throws SQLException {
        if (userId == null) {
            // Utente non loggato, recupera i prodotti dal carrello anonimo
            if (carrelloAnonimo != null) {
                return carrelloAnonimo.getListaCarrello();
            } else {
                return null; // Carrello anonimo vuoto
            }
        } else {
            // Utente loggato, recupera i prodotti dal carrello utente
            if (cartId != null) {
                carrelloBean carrelloUtente = carrelloDAO.doRetrieveByKey(cartId);
                if (carrelloUtente != null) {
                    return carrelloUtente.getProdotti();
                }
            }
            return null; // Nessun carrello o carrello vuoto per l'utente loggato
        }
    }

    // Funzione per unire il carrello anonimo con quello dell'utente loggato
    private void mergeCartWithUserCart(HttpSession session, CarrelloAnonimo carrelloAnonimo, Long cartId, prodottoCarrelloDAO prodottoCarrelloDAO, carrelloDAO carrelloDAO) throws SQLException {
        carrelloBean carrelloUtente = carrelloDAO.doRetrieveByKey(cartId);

        if (carrelloUtente == null) {
            carrelloUtente = new carrelloBean();
            carrelloUtente.setId(cartId);
            carrelloDAO.doSave(carrelloUtente);
        }

        for (prodottoCarrelloBean prodottoAnonimo : carrelloAnonimo.getListaCarrello()) {
            prodottoCarrelloBean prodottoUtente = prodottoCarrelloDAO.doRetrieveByProductId(prodottoAnonimo.getIdProdotto(), carrelloUtente.getId());

            if (prodottoUtente != null) {
                // Aggiungi quantità del carrello anonimo a quello utente
                int nuovaQuantità = prodottoUtente.getQuantita() + prodottoAnonimo.getQuantita();
                prodottoUtente.setQuantita(nuovaQuantità);
                prodottoCarrelloDAO.doUpdate(prodottoUtente);
            } else {
                // Aggiungi il prodotto anonimo nel carrello utente
                prodottoCarrelloDAO.addProduct(prodottoAnonimo.getIdProdotto(), carrelloUtente.getId(), prodottoAnonimo.getQuantita(), prodottoAnonimo.getNome());
            }
        }
    }
}