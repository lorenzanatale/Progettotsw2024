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

@WebServlet("/AggiungialcarrelloServlet")
public class Aggiungialcarrello extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("carrello.jsp");
    }

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
        double prezzo;


        try {
            prodottoId = Long.parseLong(request.getParameter("productId"));
            quantita = Integer.parseInt(request.getParameter("quantity"));
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp?message=Invalid input");
            return;
        }

        HttpSession session = request.getSession();
        Long carrelloId = (Long) session.getAttribute("carrelloId");

        if (carrelloId == null) {
            carrelloBean carrello = new carrelloBean();
            carrello.setIdUtente(0); // Anonymous user
            try {
                carrelloId = carrelloDAO.doSave(carrello);
                session.setAttribute("carrelloId", carrelloId);
            } catch (SQLException e) {
                throw new ServletException("Error creating cart", e);
            }
        }

        try {
            prodottoCarrelloDAO.addProduct(prodottoId, carrelloId, quantita, ""); // Nome can be an empty string or fetched if necessary
        } catch (SQLException e) {
            throw new ServletException("Error adding product to cart", e);
        }

        response.sendRedirect("carrello.jsp");
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
        super.destroy();
    }
}
