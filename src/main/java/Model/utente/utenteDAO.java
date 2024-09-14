package Model.utente;

import tswProj.EmailAlreadyUsedException;
import tswProj.EmptyPoolException;
import Model.abstractDAO;
import Model.interfacciaDAO;
import Model.carrello.carrelloBean;
import Model.carrello.carrelloDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;



public class utenteDAO extends abstractDAO implements interfacciaDAO<utenteBean, Long> {
    public utenteDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public utenteBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM Utente WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUtente(resultSet);
                }
            }
        }
        return null;
    }
 
    public utenteBean doRetrieveByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Utente WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUtente(resultSet);
                }
            }
        }
        return null;
    }
    
    public utenteBean doRetrieveByEmail(String email) throws SQLException {
        String query = "SELECT * FROM Utente WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUtente(resultSet);
                }
            }
        }
        return null;
    }
    
    @Override
    public Collection<utenteBean> doRetrieveAll(String order) throws SQLException {
        List<utenteBean> utenti = new ArrayList<>();
        String query = "SELECT * FROM Utente";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                utenteBean utente = getUtente(resultSet);
                utenti.add(utente);
            }
        }
        return utenti;
    }
    
    public Optional<utenteBean> doRetrieveByLogin(String username, String passwordHash) throws SQLException {
        String query = "SELECT * FROM Utente WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getUtente(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        }
    }
    
    @Override
    public long doSave(utenteBean utente) throws SQLException, EmailAlreadyUsedException {
    	if (emailExists(utente.getEmail())) {
    		throw new EmailAlreadyUsedException("L'email è già utilizzata. Si prega di scegliere un'altra email.");
        }
        String query = "INSERT INTO Utente (username, email, isAdmin, password) VALUES (?, ?, ?, ?)";
        long generatedKey = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, utente.getUsername());
            statement.setString(2, utente.getEmail());
            statement.setBoolean(3, utente.getIsAdmin());
            statement.setString(4, utente.getPassword());
            if (statement.executeUpdate() > 0){
                try (ResultSet rs = statement.getGeneratedKeys()) {
                	if (rs.next()){
                		generatedKey = rs.getInt(1);
                		carrelloBean carrello = new carrelloBean();
                		carrello.setIdUtente(generatedKey);
                		try(carrelloDAO carrelloDAO = new carrelloDAO()) {
                			carrelloDAO.doSave(carrello);
                		}
                	}
                }
            }
        }
        return generatedKey;
    }
    
    @Override
    public void doUpdate(utenteBean utente) throws SQLException {
        String query = "UPDATE Utente SET username = ?, email = ?, isAdmin = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utente.getUsername());
            statement.setString(2, utente.getEmail());
            statement.setBoolean(3, utente.getIsAdmin());
            statement.setString(4, utente.getPassword());
            statement.setLong(5, utente.getId());
            statement.executeUpdate();
        }
    }
    
    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM Utente WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private utenteBean getUtente(ResultSet resultSet) throws SQLException {
        utenteBean utente = new utenteBean();
        utente.setId(resultSet.getLong("id"));
        utente.setUsername(resultSet.getString("username"));
        utente.setEmail(resultSet.getString("email"));
        utente.setIsAdmin(resultSet.getBoolean("isAdmin"));
        utente.setPassword(resultSet.getString("password"));
        return utente;
    }
    
    public boolean emailExists(String email) throws SQLException {
    	String query = "SELECT COUNT(*) FROM Utente WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}