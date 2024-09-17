package Control;

import Model.carrello.carrelloDAO;
import Model.ordine.ordineBean;
import Model.ordine.ordineDAO;
import Model.prodottoCarrello.prodottoCarrelloBean;
import Model.prodottoOrdine.prodottoOrdineBean;
import Model.prodottoOrdine.prodottoOrdineDAO;
import Model.infoConsegna.infoConsegnaBean;
import Model.infoConsegna.infoConsegnaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ConfermaOrdine")
public class ConfermaOrdine extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if user is logged in and temporary order exists
        if (session.getAttribute("id") == null || session.getAttribute("temporaryOrder") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Long userId = (Long) session.getAttribute("id");
        ordineBean temporaryOrder = (ordineBean) session.getAttribute("temporaryOrder");
        List<prodottoCarrelloBean> cartItems = (List<prodottoCarrelloBean>) session.getAttribute("prodotti");
        Double totaleOrdine = (Double) session.getAttribute("carrelloTotale");

        if (cartItems == null || cartItems.isEmpty() || totaleOrdine == null) {
            response.sendRedirect("carrello.jsp");
            return;
        }

        try(ordineDAO orderDAO = new ordineDAO();
            prodottoOrdineDAO orderItemDAO = new prodottoOrdineDAO();
            infoConsegnaDAO infoConsegnaDAO = new infoConsegnaDAO();
            carrelloDAO carrelloDAO = new carrelloDAO()) {

            // Create and save the order in the database using the temporary order data
            ordineBean order = new ordineBean();
            order.setIdUtente(userId);
            order.setData(temporaryOrder.getData());
            order.setTotale(totaleOrdine);

            // Save delivery info
            Long infoConsegnaId = saveDeliveryInfo(request, infoConsegnaDAO, userId);
            order.setIdInfoConsegna(infoConsegnaId);

            // Save the order and get the generated ID
            Long orderId = orderDAO.doSave(order);
            if (orderId == -1) {
                throw new ServletException("Failed to create order.");
            }

            // Save order items
            for (prodottoCarrelloBean product : cartItems) {
                prodottoOrdineBean orderItem = new prodottoOrdineBean();
                orderItem.setIdOrdine(orderId);
                orderItem.setIdProdotto(product.getIdProdotto());
                orderItem.setNome(product.getNome());
                orderItem.setPrezzo(product.getPrezzo());
                orderItem.setQuantita(product.getQuantita());
                orderItemDAO.doSave(orderItem);
            }

            Long carrelloId = (Long) session.getAttribute("carrelloId"); // Retrieve the cart ID for the user
            carrelloDAO.removeAllProductsFromCart(carrelloId);

            // Clear the cart and order-related session attributes
            session.removeAttribute("cartItems");
            session.removeAttribute("carrelloTotale");
            session.removeAttribute("temporaryOrder");



            //Change disponibilità
            for (prodottoCarrelloBean product : cartItems) {
                carrelloDAO.updateDisponibilita(product);
                }


            // Forward to the order confirmation page
            request.getRequestDispatcher("ConfermaOrdine.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
    private Long saveDeliveryInfo(HttpServletRequest request, infoConsegnaDAO infoConsegnaDAO, Long userId) throws SQLException, ServletException {
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String postalCodeStr = request.getParameter("postalCode");
        String numcivicoStr = request.getParameter("numcivico");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");

        // Validate and parse postalCode
        int postalCode = 0; // Default value
        if (postalCodeStr != null && !postalCodeStr.trim().isEmpty()) {
            try {
                postalCode = Integer.parseInt(postalCodeStr);
            } catch (NumberFormatException e) {
                // Handle the exception if needed (e.g., log the error or set a default value)
                postalCode = 0; // Default or handle error
            }
        }

        // Validate and parse numcivico
        int numcivico = 0; // Default value
        if (numcivicoStr != null && !numcivicoStr.trim().isEmpty()) {
            try {
                numcivico = Integer.parseInt(numcivicoStr);
            } catch (NumberFormatException e) {
                // Handle the exception if needed (e.g., log the error or set a default value)
                numcivico = 0; // Default or handle error
            }
        }

        // Create and save delivery info
        infoConsegnaBean infoConsegna = new infoConsegnaBean();
        infoConsegna.setIdUtente(userId);
        infoConsegna.setVia(address);
        infoConsegna.setCitta(city);
        infoConsegna.setCap(postalCode);
        infoConsegna.setNumcivico(numcivico);
        infoConsegna.setDestinatario(nome + " " + cognome);

        Long infoConsegnaId = infoConsegnaDAO.doSave(infoConsegna);
        if (infoConsegnaId == null) {
            throw new ServletException("Failed to create delivery info.");
        }
        return infoConsegnaId;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to the checkout page if accessed via GET
        doPost(request, response);
    }
}
