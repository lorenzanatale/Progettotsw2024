

package Control;
import Model.prodottoCarrello.prodottoCarrelloBean;
import Model.prodottoCarrelloCheckout.prodottoCarrelloCheckoutBean;
import Model.prodottoCarrelloCheckout.prodottoCarrelloCheckoutDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/checkout")
public class Checkout extends HttpServlet {
    private prodottoCarrelloCheckoutDAO checkoutDAO;

    @Override
    public void init() throws ServletException {
        checkoutDAO = new prodottoCarrelloCheckoutDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<prodottoCarrelloBean> prodottiNelCarrello = (List<prodottoCarrelloBean>) session.getAttribute("prodottiCarrello");

        if (prodottiNelCarrello == null || prodottiNelCarrello.isEmpty()) {
            response.sendRedirect("carrello-vuoto.jsp");
            return;
        }

        double totale = 0.0;

        try {
            for (prodottoCarrelloBean carrelloBean : prodottiNelCarrello) {
                prodottoCarrelloCheckoutBean checkoutBean = checkoutDAO.doRetrieveByProductId(carrelloBean.getProdottoBean().getId(), carrelloBean.getIdCarrello());
                if (checkoutBean != null) {
                    totale += checkoutBean.getPrezzo() * carrelloBean.getQuantita();
                }
            }
            request.setAttribute("totale", totale);
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Errore durante il recupero dei dati del carrello", e);
        }
    }
}
