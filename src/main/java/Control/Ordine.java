package Control;

import Model.prodottoCarrelloCheckout.prodottoCarrelloCheckoutDAO;
import Model.prodottoCarrelloCheckout.prodottoCarrelloCheckoutBean;
import Model.prodottoOrdine.prodottoOrdineBean;
import Model.prodottoOrdine.prodottoOrdineDAO;
import Model.ordine.ordineDAO;
import Model.ordine.ordineBean;
import Model.prodottoCarrello.prodottoCarrelloBean;
import Model.utente.utenteBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/OrdineServlet")
public class Ordine extends HttpServlet {

    private prodottoCarrelloCheckoutDAO checkoutDAO;
    private prodottoOrdineDAO ordineDAO;
    private ordineDAO ordiniDAO;

    @Override
    public void init() throws ServletException {
        try {
            checkoutDAO = new prodottoCarrelloCheckoutDAO();
            ordineDAO = new prodottoOrdineDAO();
        } catch (SQLException e) {
            throw new ServletException("Errore nella connessione al database", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<prodottoCarrelloBean> prodottiNelCarrello = (List<prodottoCarrelloBean>) session.getAttribute("prodottiCarrello");

        if (prodottiNelCarrello == null || prodottiNelCarrello.isEmpty()) {
            request.setAttribute("message", "Il carrello è vuoto.");
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
            return;
        }

        // Verifica se l'utente è loggato
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Crea un nuovo ordine
            ordineBean ordine = new ordineBean();
            ordine.setIdUtente(((utenteBean) session.getAttribute("idutente")).getId()); // Supponendo che l'oggetto utente sia memorizzato nella sessione
            ordine.setData(new java.sql.Date(System.currentTimeMillis())); // Data odierna
            long idOrdine = ordineDAO.doSave(ordine);

            // Salva gli articoli dell'ordine
            for (prodottoCarrelloBean carrelloBean : prodottiNelCarrello) {
                prodottoCarrelloCheckoutBean prodottoCarrello = checkoutDAO.doRetrieveByProductId(
                        carrelloBean.getProdottoBean().getId(), carrelloBean.getIdCarrello());
                prodottoOrdineBean ordineItem = new prodottoOrdineBean();
                ordineItem.setIdOrdine(idOrdine);
                ordineItem.setIdProdotto(prodottoCarrello.getIdProdotto());
                ordineItem.setPrezzo(prodottoCarrello.getPrezzo());
                ordineItem.setQuantita(carrelloBean.getQuantita());
                ordineItem.setIva(Integer.parseInt(prodottoCarrello.getIva()));
                ordineItem.setNome(prodottoCarrello.getNome());
                ordineDAO.doSave(ordineItem);
            }

            // Pulisce il carrello
            session.removeAttribute("prodottiCarrello");

            // Redirige alla pagina di conferma dell'ordine
            response.sendRedirect("order-success.jsp");

        } catch (SQLException e) {
            throw new ServletException("Errore nella gestione dell'ordine", e);
        }
    }
}
