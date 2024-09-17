package Model.prodottoCarrello;
import tswProj.EmptyPoolException;
import Model.abstractDAO;
import Model.interfacciaDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class prodottoCarrelloDAO extends abstractDAO implements interfacciaDAO<prodottoCarrelloBean, Long> {

    public prodottoCarrelloDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public prodottoCarrelloBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM ProdottoCarrello WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCartItemFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public prodottoCarrelloBean doRetrieveByProductId(long productId, long userCartId) throws SQLException {
        String query = "SELECT * FROM ProdottoCarrello WHERE idProdotto= ? AND IdCarrello= ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCartItemFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<prodottoCarrelloBean> doRetrieveAll(String order) throws SQLException {
        List<prodottoCarrelloBean> cartItem = new ArrayList<>();
        String query = "SELECT * FROM ProdottoCarrello";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cartItem.add(extractCartItemFromResultSet(resultSet));
            }
        }
        return cartItem;
    }

    @Override
    public long doSave(prodottoCarrelloBean cartItem) throws SQLException {
        String query = "INSERT INTO ProdottoCarrello (id) VALUES (?)";
        long generatedKey = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, cartItem.getId());
            if (statement.executeUpdate() > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedKey = resultSet.getLong(1);
                }
            }
        }
        return generatedKey;
    }

    @Override
    public void doUpdate(prodottoCarrelloBean cartItem) throws SQLException {
        String query = "UPDATE ProdottoCarrello SET idProdotto = ?, idCarrello = ?, quantita = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartItem.getIdProdotto());
            statement.setLong(2, cartItem.getIdCarrello());
            statement.setInt(3, cartItem.getQuantita());
            statement.setLong(4, cartItem.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM ProdottoCarrello WHERE idProdotto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    public void addProduct(long productId, long cartId, int quantity, String nome) throws SQLException {
        // Verifica se la connessione è valida
        if (connection == null || connection.isClosed()) {
            throw new SQLException("La connessione al database non è valida.");
        }

        // Controlla se il prodotto è già presente nel carrello
        String query1 = "SELECT quantita FROM ProdottoCarrello WHERE IdCarrello = ? AND idProdotto = ?";
        boolean found = false;

        try (PreparedStatement statement = connection.prepareStatement(query1)) {
            statement.setLong(1, cartId);
            statement.setLong(2, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    quantity += resultSet.getInt("quantita");
                    found = true;
                }
            }
        }

        // Aggiorna o inserisce il prodotto
        String query;
        if (found) {
            query = "UPDATE ProdottoCarrello SET quantita = ? WHERE IdCarrello = ? AND idProdotto = ?";
        } else {
            query = "INSERT INTO ProdottoCarrello (idProdotto, IdCarrello, quantita, nome) VALUES (?, ?, ?, ?)";
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (found) {
                statement.setInt(1, quantity);
                statement.setLong(2, cartId);
                statement.setLong(3, productId);
            } else {
                statement.setLong(1, productId);
                statement.setLong(2, cartId);
                statement.setInt(3, quantity);
                statement.setString(4, nome);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Errore durante l'aggiunta o l'aggiornamento del prodotto nel carrello.", e);
        }
    }


    public void removeProduct(long productId, long cartId, int quantity) throws SQLException {
        String query1 = "SELECT * FROM ProdottoCarrello WHERE idCarrello = ? and idProdotto = ?";
        boolean found = false;
        try (PreparedStatement statement = connection.prepareStatement(query1)) {
            statement.setLong(1, cartId);
            statement.setLong(2, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    quantity = resultSet.getInt("quantita") - quantity;
                    found = true;
                }
            }
        }
        if (found) {
            if (quantity == 0) {
                this.removeProduct(productId, cartId);
                return;
            }
            String query2 = "UPDATE ProdottoCarrello SET quantita = ? WHERE idCarrello = ? AND idProdotto = ?";
            try (PreparedStatement statement = connection.prepareStatement(query2)) {
                statement.setInt(1, quantity);
                statement.setLong(2, cartId);
                statement.setLong(3, productId);
                statement.executeUpdate();
            }
        }
    }

    public void removeProduct(long productId, long cartId) throws SQLException {
        String query = "DELETE FROM ProdottoCarrello WHERE idCarrello = ? AND idProdotto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            statement.setLong(2, productId);
            statement.executeUpdate();
        }
    }

    public List<prodottoCarrelloBean> getCartItemsAsProducts(long cartId) throws SQLException {
        String query = "SELECT pc.*, p.imgPath FROM ProdottoCarrello pc " +
                "JOIN Prodotto p ON pc.idProdotto = p.id " +
                "WHERE pc.idCarrello = ?";
        List<prodottoCarrelloBean> prodotti = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    prodottoCarrelloBean prodotto = new prodottoCarrelloBean();

                    // Assumendo che 'idProdotto', 'idCarrello', 'quantita', 'nome' siano i nomi dei campi nella tabella
                    prodotto.setIdProdotto(resultSet.getLong("idProdotto")); // Assicurati che questo campo esista
                    prodotto.setIdCarrello(resultSet.getLong("idCarrello")); // Assicurati che questo campo esista
                    prodotto.setQuantita(resultSet.getInt("quantita")); // Assicurati che questo campo esista
                    prodotto.setNome(resultSet.getString("nome")); // Assicurati che questo campo esista
                    prodotto.setImgPath(resultSet.getString("imgPath")); // Assicurati che questo campo esistaprodotto.setPrezzo(resultSet.getDouble("prezzo")); // Solo se esiste un campo 'prezzo'
                    prodotti.add(prodotto);
                }
            }
        }

        return prodotti;
    }


    private prodottoCarrelloBean extractCartItemFromResultSet(ResultSet resultSet) throws SQLException {
        prodottoCarrelloBean prodottoCarrello = new prodottoCarrelloBean();
        prodottoCarrello.setId(resultSet.getLong("id"));
        prodottoCarrello.setIdProdotto(resultSet.getLong("idProdotto"));
        prodottoCarrello.setIdCarrello(resultSet.getLong("idCarrello"));
        prodottoCarrello.setQuantita(resultSet.getInt("quantita"));
        return prodottoCarrello;
    }

    public List<prodottoCarrelloBean> doRetrieveByCarrelloId(long carrelloId) {
        try {
            List<prodottoCarrelloBean> prodotti = new ArrayList<>();

            System.out.println("Prodotti nel carrello: " + prodotti.size());

            String query = "SELECT * FROM ProdottoCarrello WHERE idCarrello = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, carrelloId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        prodottoCarrelloBean prodottoCarrello = extractCartItemFromResultSet(resultSet);
                        prodotti.add(prodottoCarrello);
                    }
                }
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Long createCart(Long id) {

        return id;
    }

    public prodottoCarrelloBean getProductFromCart(Long cartId, Long prodottoId) throws SQLException {
        String query = "SELECT * FROM Prodottocarrello WHERE IdCarrello = ? AND idProdotto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, cartId);
            stmt.setLong(2, prodottoId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractCartItemFromResultSet(rs);
            }
        }
        return null; // Se non esiste
    }



}

