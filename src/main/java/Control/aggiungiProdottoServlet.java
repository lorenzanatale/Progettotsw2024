package Control;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import Model.prodotto.prodottoBean;
import Model.prodotto.prodottoDAO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

@WebServlet("/aggiungiProdottoServlet")
@MultipartConfig

public class aggiungiProdottoServlet extends HttpServlet {
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String categoria = request.getParameter("categoria");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        int disponibilita = Integer.parseInt(request.getParameter("disponibilita"));

        Part filePart = request.getPart("imgPath");
        // Percorso dove salvare il file
        String uploadDir = getServletContext().getRealPath("") + File.separator + "img" + File.separator + "prodotti";
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString() + System.currentTimeMillis();
        File file = new File(uploadDir + File.separator + fileName);

        try (InputStream fileContent = filePart.getInputStream()) {
            Files.copy(fileContent, file.toPath());
        }

        String imgPath = "img/prodotti/" + fileName;

        prodottoBean prodotto = new prodottoBean();
        
        prodotto.setNome(nome);
        prodotto.setDescrizione(descrizione);
        prodotto.setCategoria(categoria);
        prodotto.setPrezzo(prezzo);
        prodotto.setDisponibilita(disponibilita);
        prodotto.setIva("22");
        prodotto.setVisibile(true);
        prodotto.setImgPath(imgPath);

        try (prodottoDAO dao = new prodottoDAO();) {
            dao.doSave(prodotto);
        } catch (SQLException e) {
        	e.printStackTrace();
            response.getWriter().print("{\"status\": \"error\"}");
            return;
        }
        
        response.sendRedirect("catalogoServlet");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}