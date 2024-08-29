package Model.infoConsegna;

import Model.abstractDAO;
import Model.interfacciaDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class infoConsegnaDAO extends abstractDAO implements interfacciaDAO<infoConsegnaBean, Long> {

	@Override
	public infoConsegnaBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM InfoConsegna WHERE idInfoConsegna = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return getByResultSet(resultSet);
                }
            }
        }
        return null;
    }
	
	 @Override
	    public Collection<infoConsegnaBean> doRetrieveAll(String order) throws SQLException {
	        String query = "SELECT * FROM InfoConsegna";
	        List<infoConsegnaBean> infoConsegnaBeans = new ArrayList<>();
	        try(Statement statement = connection.createStatement()) {
	            statement.execute(query);
	            try (ResultSet resultSet = statement.getResultSet()) {
	                while (resultSet.next()) {
	                    infoConsegnaBeans.add(getByResultSet(resultSet));
	                }
	            }
	        }
	        return infoConsegnaBeans;
	    }

	    public List<infoConsegnaBean> doRetrieveAllByUser(long idUser) throws SQLException {
	        String query = "SELECT * FROM InfoConsegna WHERE idUtente = ?";
	        List<infoConsegnaBean> infoConsegnaBeans = new ArrayList<>();
	        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setLong(1, idUser);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    infoConsegnaBeans.add(getByResultSet(resultSet));
	                }
	            }
	        }
	        return infoConsegnaBeans;
	    }
	    
	    public infoConsegnaBean doRetrieveDefault(long idUser) throws SQLException {
	        String query = "SELECT * FROM InfoConsegna WHERE idUtente = ? AND isDefault = 1";
	        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setLong(1, idUser);
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    return getByResultSet(resultSet);
	                }
	            }
	        }
	        return null;
	    }

	    @Override
	    public long doSave(infoConsegnaBean product) throws SQLException {
	        String query = "INSERT INTO InfoConsegna(citta, cap, via, numcivico, destinatario, idUtente, isDefault) VALUES (?, ?, ?, ?, ?, ?, ?)";
	        long generatedKey = -1;
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            preparedStatement.setString(1, product.getCitta());
	            preparedStatement.setInt(2, product.getCap());
	            preparedStatement.setString(3, product.getVia());
	            preparedStatement.setInt(4, product.getNumcivico());
	            preparedStatement.setString(5, product.getDestinatario());
	            preparedStatement.setLong(6, product.getIdUtente());
	            preparedStatement.setInt(7, product.isDefault() ? 1 : 0);
	            if (preparedStatement.executeUpdate() > 0){
	                ResultSet resultSet = preparedStatement.getGeneratedKeys();
	                if (resultSet.next()){
	                    generatedKey = resultSet.getLong(1);
	                }
	            }
	        }
	        return generatedKey;
	    }
	    
	    @Override
	    public void doUpdate(infoConsegnaBean product) throws SQLException {
	        String query = "UPDATE InfoConsegna SET citta = ?, cap = ?, via = ?, numcivico = ?, destinatario = ?, idUtente = ?, isDefault = ? WHERE id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setString(1, product.getCitta());
	            preparedStatement.setInt(2, product.getCap());
	            preparedStatement.setString(3, product.getVia());
	            preparedStatement.setInt(4, product.getNumcivico());
	            preparedStatement.setString(5, product.getDestinatario());
	            preparedStatement.setLong(6, product.getIdUtente());
	            preparedStatement.setBoolean(7, product.isDefault());
	            preparedStatement.setLong(8, product.getId());
	            preparedStatement.executeUpdate();
	        }
	    }
	    
	    @Override
	    public boolean doDelete(Long code) throws SQLException {
	        String query = "DELETE FROM InfoConsegna WHERE id = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setLong(1, code);
	            return preparedStatement.executeUpdate() > 0;
	        }
	    }

	    public infoConsegnaBean getByResultSet(ResultSet resultSet) throws SQLException {
	        infoConsegnaBean infoConsegnaBean = new infoConsegnaBean();
	        infoConsegnaBean.setId(resultSet.getLong("id"));
	        infoConsegnaBean.setCap(resultSet.getInt("cap"));
	        infoConsegnaBean.setCitta(resultSet.getString("citta"));
	        infoConsegnaBean.setVia(resultSet.getString("via"));
	        infoConsegnaBean.setNumcivico(resultSet.getInt("numcivico"));
	        infoConsegnaBean.setDestinatario(resultSet.getString("destinatario"));
	        infoConsegnaBean.setIdUtente(resultSet.getLong("idUtente"));
	        infoConsegnaBean.setDefault(resultSet.getInt("isDefault") == 1);
	        return infoConsegnaBean;
	    }
}
