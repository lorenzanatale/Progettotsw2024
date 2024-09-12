package Control;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import Model.prodotto.*;
import Model.prodottoCarrello.prodottoCarrelloDAO;


@WebServlet(name = "AggiungialcarrelloServlet", value = "/aggiungi-al-carrello-servlet")
public class Aggiungialcarrello extends HttpServlet {
    private String message;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        prodottoCarrelloDAO dao = new prodottoCarrelloDAO();
        long productId = Long.parseLong(request.getParameter("productId"));
        long cartId = Long.parseLong(request.getParameter("cartId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            //aggiungiamo i prodotti
            dao.addProduct(productId,cartId,quantity);
            response.sendRedirect("/prodottoServlet?id="+productId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            request.getRequestDispatcher("/catalogoServlet").forward(request,response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
    public void destroy() {
    }
}