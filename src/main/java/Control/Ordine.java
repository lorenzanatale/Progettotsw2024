package Control;

import Model.infoConsegna.infoConsegnaBean;
import Model.ordine.ordineBean;
import Model.ordine.ordineDAO;
import Model.prodottoCarrello.prodottoCarrelloBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/OrdineServlet")
public class Ordine extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if user is logged in
        if (session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Long userId = (Long) session.getAttribute("id");
        List<prodottoCarrelloBean> cartItems = (List<prodottoCarrelloBean>) session.getAttribute("prodotti");

        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect("carrello.jsp");
            return;
        }

        try {
            // Create a temporary order in session
            ordineBean temporaryOrder = new ordineBean();
            temporaryOrder.setIdUtente(userId);
            temporaryOrder.setData(new java.sql.Date(System.currentTimeMillis()));

            // Calculate the total order amount
            double totaleOrdine = 0.0;
            for (prodottoCarrelloBean product : cartItems) {
                totaleOrdine += product.getPrezzo() * product.getQuantita();
            }
            temporaryOrder.setTotale(totaleOrdine);

            // Store the temporary order in session for later confirmation
            session.setAttribute("temporaryOrder", temporaryOrder);

            // Set the total in the session for use in checkout.jsp
            session.setAttribute("carrelloTotale", totaleOrdine);

            // Get delivery information from the request parameters
            String nome = request.getParameter("nome");
            String cognome = request.getParameter("cognome");
            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String postalCodeStr = request.getParameter("postalCode");
            String numcivicoStr = request.getParameter("numcivico");

            // Create and store delivery info in the session
            infoConsegnaBean deliveryInfo = new infoConsegnaBean();
            deliveryInfo.setIdUtente(userId);
            deliveryInfo.setDestinatario(nome + " " + cognome);
            deliveryInfo.setVia(address);
            deliveryInfo.setCitta(city);

            // Check and parse postalCode
            if (postalCodeStr != null && !postalCodeStr.trim().isEmpty()) {
                try {
                    deliveryInfo.setCap(Integer.parseInt(postalCodeStr));
                } catch (NumberFormatException e) {
                    // Handle the exception if needed (e.g., log the error or set a default value)
                    deliveryInfo.setCap(0); // or any other default value
                }
            } else {
                deliveryInfo.setCap(0); // or any other default value
            }

            // Check and parse numcivico
            if (numcivicoStr != null && !numcivicoStr.trim().isEmpty()) {
                try {
                    deliveryInfo.setNumcivico(Integer.parseInt(numcivicoStr));
                } catch (NumberFormatException e) {
                    // Handle the exception if needed (e.g., log the error or set a default value)
                    deliveryInfo.setNumcivico(0); // or any other default value
                }
            } else {
                deliveryInfo.setNumcivico(0); // or any other default value
            }

            session.setAttribute("deliveryInfo", deliveryInfo);

            // Redirect to checkout page for delivery information input
            response.sendRedirect(request.getContextPath() + "/checkout.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect to the cart page if accessed via GET
        response.sendRedirect("carrello.jsp");
    }
}
