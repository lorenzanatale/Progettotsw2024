import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

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