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

	public long doRetrieveByUserId(long userId) throws SQLException {
		String query = "SELECT id FROM Carrello WHERE IdUtente = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, userId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("id");
				}
			}
		}
		throw new RuntimeSQLException("Cart not found for user ID: " + userId);
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
		String query = "INSERT INTO Carrello (IdUtente) VALUES (?)";
		long generatedKey = -1;

		try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			// Gestione del valore di IdUtente
			if (carrello.getIdUtente() == 0) {
				statement.setNull(1, Types.BIGINT); // Imposta il valore a NULL per carrelli anonimi
			} else {
				statement.setLong(1, carrello.getIdUtente());
			}

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

		// Metodo per eliminare tutti i prodotti associati a un carrello
		public void removeAllProductsFromCart(long carrelloId) throws SQLException {
			String query = "DELETE FROM prodottocarrello WHERE idCarrello = ?";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setLong(1, carrelloId);
				int rowsAffected = statement.executeUpdate();
				if (rowsAffected == 0) {
					throw new SQLException("No products were removed. Check if the cart ID is correct.");
				}
			}
		}
	}

