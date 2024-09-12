package Model.prodottoOrdine;

import tswProj.EmptyPoolException;
import Model.abstractDAO;
import Model.interfacciaDAO;
import java.sql.*;
import java.util.*;

public class prodottoOrdineDAO extends abstractDAO implements interfacciaDAO <prodottoOrdineBean, Long> {

	public prodottoOrdineDAO() throws SQLException {
		super();
	}
	
    @Override
    public prodottoOrdineBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM ProdottoOrdine WHERE idItem = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractOrderItemFromResultSet(resultSet);
                }
            }
        }
        return null;
    }
    
    @Override
    public Collection<prodottoOrdineBean> doRetrieveAll(String order) throws SQLException {
        List<prodottoOrdineBean> orderItems = new ArrayList<>();
        String query = "SELECT * FROM ProdottoOrdine";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
            	prodottoOrdineBean orderItem = extractOrderItemFromResultSet(resultSet);
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }
    
    @Override
    public long doSave(prodottoOrdineBean orderItem) throws SQLException {
        String query = "INSERT INTO ProdottoOrdine (IdOrdine, IdProdotto, Prezzo, quantità, Iva, Nome) VALUES (?, ?, ?, ?, ?, ?)";
        long generatedKey = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, orderItem.getIdOrdine());
            statement.setLong(2, orderItem.getIdProdotto());
            statement.setDouble(3, orderItem.getPrezzo());
            statement.setLong(4, orderItem.getQuantita());
            statement.setString(5, Integer.toString(orderItem.getIva()));
            statement.setString(6, orderItem.getNome());
            if (statement.executeUpdate() > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    generatedKey = resultSet.getLong(1);
                }
            }
        }
        return generatedKey;
    }

    public void doSaveAll(List<prodottoOrdineBean> orderItems) throws SQLException {
        connection.setAutoCommit(false);

        try{
            for (prodottoOrdineBean orderItem : orderItems) {
                doSave(orderItem);
            }
            connection.commit();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void doUpdate(prodottoOrdineBean orderItem) throws SQLException {
        String query = "UPDATE ProdottoOrdine SET idProdotto = ?, IdOrdine = ?, Prezzo = ?, Quantita = ?, iva = ?, Nome = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderItem.getIdProdotto());
            statement.setLong(2, orderItem.getIdOrdine());
            statement.setDouble(3, orderItem.getPrezzo());
            statement.setLong(4, orderItem.getQuantita());
            statement.setString(5, Integer.toString(orderItem.getIva()));
            statement.setString(6, orderItem.getNome());
            statement.setLong(7, orderItem.getId());
            statement.executeUpdate();
        }
    }
    
    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM ProdottoOrdine WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }
    
    private prodottoOrdineBean extractOrderItemFromResultSet(ResultSet resultSet) throws SQLException {
    	prodottoOrdineBean orderItem = new prodottoOrdineBean();
        orderItem.setId(resultSet.getLong("id"));
        orderItem.setIdProdotto(resultSet.getLong("idProdotto"));
        orderItem.setIdOrdine(resultSet.getLong("IdOrdine"));
        orderItem.setPrezzo(resultSet.getDouble("Prezzo"));
        orderItem.setQuantita(resultSet.getInt("Quantita"));
        orderItem.setIva(Integer.parseInt(resultSet.getString("Iva")));
        orderItem.setNome(resultSet.getString("Nome"));
        return orderItem;
    }
    
    public Collection<prodottoOrdineBean> doRetrieveByOrder(long idOrder) throws SQLException {
        List<prodottoOrdineBean> orderItems = new ArrayList<>();
        String query = "SELECT * FROM ProdottoOrdine WHERE IdOrdine = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, idOrder);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                	prodottoOrdineBean orderItem = extractOrderItemFromResultSet(resultSet);
                    orderItems.add(orderItem);
                }
            }
        }
        return orderItems;
    }

}
