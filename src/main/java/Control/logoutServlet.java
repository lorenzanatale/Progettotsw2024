package Control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logoutServlet")
public class logoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // Rimuovi solo le informazioni dell'utente
            session.removeAttribute("user");
            // Non rimuovere l'ID del carrello dalla sessione
        }
        response.sendRedirect(request.getContextPath() + "/home.jsp");
    }
}
