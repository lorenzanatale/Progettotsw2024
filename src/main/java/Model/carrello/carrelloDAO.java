package Model.carrello;
import tswProj.EmptyPoolException;
import tswProj.RuntimeSQLException;
import Model.abstractDAO;
import Model.interfacciaDAO;

import java.sql.*;
import java.util.*;

public class carrelloDAO extends abstractDAO implements interfacciaDAO<carrelloBean, Long> {
	public carrelloDAO() throws EmptyPoolException {
		super();
	}
	public long doRetriveByUserId(long id) throws SQLException {
		String query = "select * from carrello where IdUtente= ?";
		try(PreparedStatement statement = connection.prepareStatement(query)){
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("IdUtente");
				}
			}
		}
        return 0;
    }
	
	@Override
	public carrelloBean doRetrieveByKey(long id) throws SQLException {
		String query = "SELECT * FROM Carrello WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return extractCarrelloFromResultSet(resultSet);
				}
			}
		}
		return null;
	}

	@Override
	public Collection<carrelloBean> doRetrieveAll(String order) throws SQLException {
		List<carrelloBean> carrelli = new ArrayList<>();
		String query = "SELECT * FROM Carrello";
		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				carrelloBean carrello = extractCarrelloFromResultSet(resultSet);
				carrelli.add(carrello);
			}
		}
		return carrelli;
	}

	@Override
	public long doSave(carrelloBean carrello) throws SQLException {
		String query = "INSERT INTO Carrello(idUtente) VALUES(?)";
		long generatedKey= -1;
		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setLong(1, carrello.getIdUtente());
			if (statement.executeUpdate() > 0) {
				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					if (resultSet.next()) {
						generatedKey = resultSet.getLong(1);
					}
				}
			}
		}
		return generatedKey;
	}
	
	@Override
	public void doUpdate(carrelloBean carrello) throws SQLException {
		String query = "UPDATE Carrello SET idUtente=? WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, carrello.getIdUtente());
			statement.setLong(2, carrello.getId());
			statement.executeUpdate();
		}
	}
	
	@Override
	public boolean doDelete(Long id) throws SQLException {
		String query = "DELETE FROM Carrello WHERE id=?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		}
	}
	
	private carrelloBean extractCarrelloFromResultSet(ResultSet resultSet) throws SQLException {
		carrelloBean carrello = new carrelloBean();
		carrello.setId(resultSet.getLong("id"));
		carrello.setIdUtente(resultSet.getLong("idUtente"));
		return carrello;
	}
	
    public long getCarrelloId(long userId) throws SQLException {
        String query = "SELECT * FROM Carrello WHERE IdUtente = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        }
        throw new RuntimeSQLException("CartId not found");
    }
}
