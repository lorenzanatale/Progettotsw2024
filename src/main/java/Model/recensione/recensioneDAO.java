package Model.recensione;

import tswProj.EmptyPoolException;
import Model.abstractDAO;
import Model.interfacciaDAO;

import java.sql.*;
import java.util.*;

public class recensioneDAO extends abstractDAO implements interfacciaDAO<recensioneBean, Long> {

	public recensioneDAO() throws EmptyPoolException {
        super();
    }
	
	@Override
    public recensioneBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM Recensione WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getRecensione(resultSet);
                }
            }
        }
        return null;
    }
	
	@Override
    public Collection<recensioneBean> doRetrieveAll(String order) throws SQLException {
        List<recensioneBean> recensioni = new ArrayList<>();
        String query = "SELECT * FROM Recensione";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                recensioneBean recensione = getRecensione(resultSet);
                recensioni.add(recensione);
            }
        }
        return recensioni;
    }
    
    public Collection<recensioneBean> doRetrieveByProduct(long idProdotto) throws SQLException {
        List<recensioneBean> recensioni = new ArrayList<>();
        String query = "SELECT * FROM Recensione WHERE idProdotto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, idProdotto);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recensioneBean recensione = getRecensione(resultSet);
                    recensioni.add(recensione);
                }
            }
        }
        return recensioni;
    }
    
    @Override
    public long doSave(recensioneBean recensione) throws SQLException {
        String query = "INSERT INTO Recensione (idUtente, Titolo, Commento, Valutazione, Data, idProdotto) VALUES (?, ?, ?, ?, ?, ?)";
        long generatedKey = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, recensione.getIdUtente());
            statement.setString(2, recensione.getTitolo());
            statement.setString(3, recensione.getCommento());
            statement.setDouble(4, recensione.getValutazione());
            statement.setDate(5, recensione.getData());
            statement.setLong(6, recensione.getIdProdotto());
            if (statement.executeUpdate() > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    generatedKey = rs.getLong(1);
                }
            }
        }
        return generatedKey;
    }
    
    @Override
    public void doUpdate(recensioneBean recensione) throws SQLException {
        String query = "UPDATE Recensione SET idUtente = ?, Titolo = ?, Commento = ?, Valutazione = ?, Data = ?, idProdotto = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, recensione.getIdUtente());
            statement.setString(2, recensione.getTitolo());
            statement.setString(3, recensione.getCommento());
            statement.setDouble(4, recensione.getValutazione());
            statement.setDate(5, recensione.getData());
            statement.setLong(6, recensione.getIdProdotto());
            statement.setLong(7, recensione.getId());
            statement.executeUpdate();
        }
    }
    
    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM Recensione WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }
    
    private recensioneBean getRecensione(ResultSet resultSet) throws SQLException {
        recensioneBean recensione = new recensioneBean();
        recensione.setId(resultSet.getLong("id"));
        recensione.setIdUtente(resultSet.getLong("idUtente"));
        recensione.setTitolo(resultSet.getString("Titolo"));
        recensione.setCommento(resultSet.getString("Commento"));
        recensione.setValutazione(resultSet.getDouble("Valutazione"));
        recensione.setData(resultSet.getDate("Data"));
        recensione.setIdProdotto(resultSet.getLong("idProdotto"));
        return recensione;
    }

    public boolean hasUserReviewedProduct(long userId, long productId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Recensione WHERE idUtente = ? AND idProdotto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}