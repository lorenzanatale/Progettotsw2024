package Control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/Pagamento")
public class Pagamento extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if user is logged in
        if (session.getAttribute("id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get the order ID from the request
        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.isEmpty()) {
            response.sendRedirect("error.jsp?message=Invalid%20order%20ID");
            return;
        }

        long orderId;
        try {
            orderId = Long.parseLong(orderIdParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp?message=Invalid%20order%20ID");
            return;
        }

        // Process the payment
        boolean paymentSuccess = processPayment(orderId);
        if (!paymentSuccess) {
            response.sendRedirect("error.jsp?message=Payment%20failed");
            return;
        }

        // Redirect to the order confirmation servlet
        response.sendRedirect("ConfermaOrdine?orderId=" + orderId);
    }

    private boolean processPayment(long orderId) {
        // Simulate payment processing
        // Replace this with actual payment processing logic
        // For example, you might interact with a payment gateway API here
        return true; // Assume payment is always successful for this example
    }
}
