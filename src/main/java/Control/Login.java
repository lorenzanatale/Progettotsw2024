package Control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //sessione
        HttpSession session = request.getSession(); // Ottiene la sessione, o ne crea una nuova
        session.setAttribute("username", username); // Memorizza l'username nella sessione
        session.setMaxInactiveInterval(30 * 60); // La sessione scadrà dopo 30 minuti di inattività

        // Gestione dei cookie
        Cookie userCookie = new Cookie("username", username);
        userCookie.setMaxAge(24 * 60 * 60); // Il cookie sarà valido per 1 giorno
        response.addCookie(userCookie); // Aggiunge il cookie alla risposta


        if(username.equals("admin") && password.equals("admin")) {
            request.getRequestDispatcher("success.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");


    }

    public void destroy() {
    }
}