package Control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.ordine.ordineBean;
import Model.ordine.ordineDAO;
import Model.prodottoOrdine.prodottoOrdineBean;
import Model.prodottoOrdine.prodottoOrdineDAO;
import Model.prodottoCarrelloCheckout.prodottoCarrelloCheckoutBean;
import Model.prodottoCarrelloCheckout.prodottoCarrelloCheckoutDAO;

@WebServlet("/CheckoutServlet")
public class Checkout extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            // User is not logged in, redirect to login page
            response.sendRedirect("login.jsp");
        } else {
            // User is logged in, proceed to checkout
            response.sendRedirect("checkout.jsp");
        }
    }
}

