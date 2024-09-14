package Model.prodotto;

import tswProj.EmptyPoolException;
import tswProj.OutOfStockException;
import Model.abstractDAO;
import Model.interfacciaDAO;

import java.sql.*;
import java.util.*;

public class prodottoDAO extends abstractDAO implements interfacciaDAO<prodottoBean, Long> {

	public prodottoDAO() throws EmptyPoolException {
		super();
	}
	
	@Override
	public prodottoBean doRetrieveByKey(long id) throws SQLException {
		String query = "SELECT * FROM Prodotto WHERE id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return getProdotto(resultSet);
				}
			}
		}
		return null;
	}
	
	 public Map<prodottoBean, Integer> doRetrieveByKeys(Map<Long, Integer> productsWithQuantity) throws SQLException {
	        Map<prodottoBean, Integer> prodottoBeans = new HashMap<>();
	        for (Map.Entry<Long, Integer> entry : productsWithQuantity.entrySet()) {
	            prodottoBean p = doRetrieveByKey(entry.getKey());
	            if (p != null) {
	                prodottoBeans.put(p, entry.getValue());
	            }
	        }
	        return prodottoBeans;
	    }
	 
	 public List<prodottoBean> doRetrieveByName(String name) throws SQLException {
		 List<prodottoBean> prodottoBeans = new ArrayList<>();
		 String query = "SELECT * FROM Prodotto WHERE LOWER(Nome) LIKE LOWER(?) LIMIT 5";
			try (PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, "%" + name + "%");
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						prodottoBeans.add(getProdotto(resultSet));
					}
				}
			}
			return prodottoBeans;
	 }
	 
	 @Override
	    public Collection<prodottoBean> doRetrieveAll(String order) throws SQLException {
	        List<prodottoBean> prodotti = new ArrayList<>();
	        String query = "SELECT * FROM Prodotto";
	        try (PreparedStatement statement = connection.prepareStatement(query);
	             ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                prodottoBean prodotto = getProdotto(resultSet);
	                prodotti.add(prodotto);
	            }
	        }
	        return prodotti;
	    }

	    public Collection<prodottoBean> doRetrieveAllByCategory(String category) throws SQLException {
	        List<prodottoBean> prodotti = new ArrayList<>();
	        String query = "SELECT * FROM Prodotto WHERE Categoria = ?";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, category);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
	                    prodottoBean prodotto = getProdotto(resultSet);
	                    prodotti.add(prodotto);
	                }
	            }
	        }

	        return prodotti;
	    }
	    
	    @Override
	    public long doSave(prodottoBean prodotto) throws SQLException {
	        String query = "INSERT INTO Prodotto (Nome, Disponibilità, Categoria, IVA, Prezzo, imgPath, Descrizione, visibile) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        long generatedKey = -1;
	        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
	            statement.setString(1, prodotto.getNome());
	            statement.setInt(2, prodotto.getDisponibilita());
	            statement.setString(3, prodotto.getCategoria());
	            statement.setString(4, prodotto.getIva());
	            statement.setDouble(5, prodotto.getPrezzo());
	            statement.setString(6, prodotto.getImgPath());
	            statement.setString(7, prodotto.getDescrizione());
	            statement.setInt(8, prodotto.isVisibile() ? 1 : 0);
	            if (statement.executeUpdate() > 0){
	                ResultSet rs = statement.getGeneratedKeys();
	                if (rs.next()){
	                    generatedKey = rs.getLong(1);
	                }
	            }
	        }
	        return generatedKey;
	    }
	    
	    @Override
	    public void doUpdate(prodottoBean prodotto) throws SQLException {

	        String query = "UPDATE Prodotto SET Nome = ?, Disponibilità = ?, Categoria = ?, IVA = ?, Prezzo = ?, imgPath = ?, descrizione = ?, visibile = ? WHERE id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, prodotto.getNome());
	            statement.setInt(2, prodotto.getDisponibilita());
	            statement.setString(3, prodotto.getCategoria());
	            statement.setString(4, String.valueOf(prodotto.getIva()));
	            statement.setDouble(5, prodotto.getPrezzo());
	            statement.setString(6, prodotto.getImgPath());
	            statement.setString(7, prodotto.getDescrizione());
	            statement.setInt(8, prodotto.isVisibile() ? 1 : 0);
	            statement.setLong(9, prodotto.getId());
	            statement.executeUpdate();
	        }
	    }
	    
	    public void doUpdateQuantities(Map<prodottoBean, Integer> toUpdate) throws SQLException, OutOfStockException {
	        connection.setAutoCommit(false);
	        for (Map.Entry<prodottoBean, Integer> entry : toUpdate.entrySet()) {
	            prodottoBean prodotto = doRetrieveByKey(entry.getKey().getId());
	            if (prodotto == null || prodotto.getDisponibilita() < entry.getValue()) {
	                connection.rollback();
	                connection.setAutoCommit(true);
	                throw new OutOfStockException("Disponibilità per il prodotto con id " + entry.getKey().getId() + " terminata");
	            }
	            prodotto.setDisponibilita(prodotto.getDisponibilita() - entry.getValue());
	            doUpdate(prodotto);
	        }
	        connection.commit();
	        connection.setAutoCommit(true);
	    }
	    
	    @Override
	    public boolean doDelete(Long id) throws SQLException {
	        String query = "DELETE FROM Prodotto WHERE id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setLong(1, id);
	            int rowsDeleted = statement.executeUpdate();
	            return rowsDeleted > 0;
	        }
	    }
	    
	    private prodottoBean getProdotto(ResultSet resultSet) throws SQLException {
	        prodottoBean prodotto = new prodottoBean();
	        prodotto.setId(resultSet.getLong("id"));
	        prodotto.setNome(resultSet.getString("Nome"));
	        prodotto.setDisponibilita(resultSet.getInt("Disponibilità"));
	        prodotto.setCategoria(resultSet.getString("Categoria"));
	        prodotto.setIva(resultSet.getString("IVA"));
	        prodotto.setPrezzo(resultSet.getLong("Prezzo"));
	        prodotto.setImgPath(resultSet.getString("imgPath"));
	        prodotto.setDescrizione(resultSet.getString("descrizione"));
	        prodotto.setVisibile(resultSet.getInt("visibile") == 1);
	        return prodotto;
	    }

	    public void changeVisibility(long id, boolean isVisible) throws SQLException {
	        String query = "UPDATE Prodotto SET visibile = ? WHERE id = ?";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, isVisible ? 1 : 0);
	            statement.setLong(2, id);
	            statement.executeUpdate();
	        }
	    }
	    
		public List<prodottoBean> doRetrieveByNome(String nome) throws SQLException {
	    try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Prodotto WHERE nome LIKE ?")) {
	        ps.setString(1, "%" + nome + "%");
	        ResultSet rs = ps.executeQuery();
	        List<prodottoBean> prodotti = new ArrayList<>();
	        while (rs.next()) {
	        	prodottoBean temp = new prodottoBean();
	        	
	        	temp.setId(rs.getLong("id"));
	        	temp.setNome(rs.getString("nome"));
	        	temp.setDisponibilita(rs.getInt("disponibilita"));
	        	temp.setCategoria(rs.getString("categoria"));
	        	temp.setIva(rs.getString("iva"));
	        	temp.setPrezzo(rs.getDouble("prezzo"));
	        	temp.setImgPath(rs.getString("imgPath"));
	        	temp.setDescrizione(rs.getString("descrizione"));
	        	temp.setVisibile(rs.getBoolean("visibile"));
	        	
	            prodotti.add(temp);
	        }
	        return prodotti;
	    }
	}
	
}