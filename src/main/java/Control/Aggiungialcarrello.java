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

@WebServlet(name = "AggiungialcarrelloServlet", value = "/aggiungi-al-carrello-servlet")
public class Aggiungialcarrello extends HttpServlet {

    private prodottoCarrelloDAO prodottoCarrelloDAO;
    private carrelloDAO carrelloDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            prodottoCarrelloDAO = new prodottoCarrelloDAO();
            carrelloDAO = new carrelloDAO();
        } catch (Exception e) {
            throw new ServletException("Errore nell'inizializzazione dei DAO", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Validazione parametri di input
            String productIdParam = request.getParameter("productId");
            String quantityParam = request.getParameter("quantity");
            String nome = request.getParameter("nome");

            if (productIdParam == null || quantityParam == null || nome == null || productIdParam.isEmpty() || quantityParam.isEmpty()) {
                throw new IllegalArgumentException("Parametri non validi");
            }

            long productId = Long.parseLong(productIdParam);
            int quantity = Integer.parseInt(quantityParam);

            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");
            Long cartId = (Long) session.getAttribute("cartId");
            CarrelloAnonimo carrelloAnonimo = (CarrelloAnonimo) session.getAttribute("cartAnonimo");

            if (userId == null) {
                // Gestione del carrello anonimo
                if (carrelloAnonimo == null) {
                    carrelloAnonimo = new CarrelloAnonimo();
                    session.setAttribute("cartAnonimo", carrelloAnonimo);
                }

                prodottoCarrelloBean prodottoCarrello = new prodottoCarrelloBean();
                prodottoCarrello.setQuantita(quantity);
                prodottoCarrello.setIdProdotto(productId);
                prodottoCarrello.setNome(nome);

                // Verifica se il prodotto è già nel carrello anonimo
                boolean found = false;
                for (prodottoCarrelloBean p : carrelloAnonimo.getListaCarrello()) {
                    if (p.getIdProdotto() == productId) {
                        p.setQuantita(p.getQuantita() + quantity);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    // Aggiungi il prodotto se non esiste già nel carrello
                    carrelloAnonimo.addProduct(prodottoCarrello);
                }

                session.setAttribute("cartAnonimo", carrelloAnonimo);

            } else {
                // Gestione del carrello dell'utente loggato
                if (cartId != null) {
                    carrelloBean carrelloUtente = carrelloDAO.doRetrieveByKey(cartId);

                    if (carrelloUtente == null) {
                        carrelloUtente = new carrelloBean();
                        carrelloUtente.setId(cartId);
                        carrelloDAO.doSave(carrelloUtente);
                    }

                    // Verifica se il prodotto è già nel carrello dell'utente
                    prodottoCarrelloBean existingItem = prodottoCarrelloDAO.doRetrieveByProductId(productId, cartId);
                    if (existingItem != null) {
                        // Aggiorna la quantità esistente
                        existingItem.setQuantita(existingItem.getQuantita() + quantity);
                        prodottoCarrelloDAO.doUpdate(existingItem); // Assicurati che funzioni correttamente
                    } else {
                        // Aggiungi il prodotto al carrello dell'utente
                        prodottoCarrelloDAO.addProduct(productId, cartId, quantity, nome); // Assicurati che funzioni correttamente
                    }

                    // Trasferisci i prodotti dal carrello anonimo al carrello dell'utente
                    if (carrelloAnonimo != null) {
                        mergeCartWithUserCart(session, carrelloAnonimo, cartId);
                    }

                    // Rimuovi il carrello anonimo dalla sessione
                    session.removeAttribute("cartAnonimo");
                }
            }

            // Reindirizza alla pagina del carrello
            response.sendRedirect(request.getContextPath() + "/Visualcarrello-servlet");

        } catch (SQLException e) {
            e.printStackTrace(); // Log error for debugging
            request.setAttribute("errorMessage", "Errore durante l'aggiunta del prodotto al carrello: " + e.getMessage());
            try {
                request.getRequestDispatcher("/errore.jsp").forward(request, response);
            } catch (ServletException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log any other exceptions
            request.setAttribute("errorMessage", "Errore durante l'elaborazione della richiesta: " + e.getMessage());
            try {
                request.getRequestDispatcher("/errore.jsp").forward(request, response);
            } catch (ServletException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void mergeCartWithUserCart(HttpSession session, CarrelloAnonimo carrelloAnonimo, Long cartId) throws SQLException {
        carrelloBean carrelloUtente = carrelloDAO.doRetrieveByKey(cartId);

        if (carrelloUtente == null) {
            carrelloUtente = new carrelloBean();
            carrelloUtente.setId(cartId);
            carrelloDAO.doSave(carrelloUtente);
        }

        for (prodottoCarrelloBean prodottoAnonimo : carrelloAnonimo.getListaCarrello()) {
            prodottoCarrelloBean prodottoUtente = prodottoCarrelloDAO.doRetrieveByProductId(prodottoAnonimo.getIdProdotto(), carrelloUtente.getId());

            if (prodottoUtente != null) {
                // Aggiungi la quantità del carrello anonimo a quella del carrello utente
                int nuovaQuantità = prodottoUtente.getQuantita() + prodottoAnonimo.getQuantita();
                prodottoUtente.setQuantita(nuovaQuantità);
                prodottoCarrelloDAO.doUpdate(prodottoUtente);
            } else {
                // Aggiungi il prodotto anonimo al carrello dell'utente
                prodottoCarrelloDAO.addProduct(prodottoAnonimo.getIdProdotto(), carrelloUtente.getId(), prodottoAnonimo.getQuantita(), prodottoAnonimo.getNome());
            }
        }
    }
}
