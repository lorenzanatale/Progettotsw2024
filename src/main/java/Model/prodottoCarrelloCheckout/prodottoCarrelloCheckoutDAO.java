package Model.prodottoCarrelloCheckout;

import Model.abstractDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class prodottoCarrelloCheckoutDAO extends abstractDAO {
	
	public prodottoCarrelloCheckoutBean doRetrieveByProductId(long productId, long cartId) throws SQLException {
        String query = "SELECT Prodotto.id AS \"idProdotto\", ProdottoCarrello.IdCarrello, ProdottoCarrello.id AS \"idProdottoCarrello\", ProdottoCarrello.Quantita, Prodotto.Categoria, Prodotto.Descrizione, Prodotto.Disponibilità, Prodotto.imgPath, Prodotto.IVA, Prodotto.Nome, Prodotto.Prezzo, Prodotto.Visibile  FROM ProdottoCarrello JOIN Prodotto ON Prodotto.id = ProdottoCarrello.idProdotto WHERE ProdottoCarrello.idCarrello= ? AND Prodotto.id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, cartId);
            pstmt.setLong(2, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return getResolvedCartItemFromRS(rs);
            }
            return null;
        }
    }
	
	 public Map<prodottoCarrelloCheckoutBean, Integer> doRetrieveByProductIds(Map<Long, Integer> productIds, long cartId) throws SQLException {
	        Map<prodottoCarrelloCheckoutBean, Integer> cartItems = new HashMap<>();
	        for (Map.Entry<Long, Integer> entry : productIds.entrySet()) {
	        	prodottoCarrelloCheckoutBean res = doRetrieveByProductId(entry.getKey(), cartId);
	            if (res != null) {
	                cartItems.put(res, entry.getValue());
	            }
	        }
	        return cartItems;
	 }
	 
	 private prodottoCarrelloCheckoutBean getResolvedCartItemFromRS(ResultSet rs) throws SQLException {
		 
		 prodottoCarrelloCheckoutBean prodottoCarrelloCheckoutBean = new prodottoCarrelloCheckoutBean();

		 prodottoCarrelloCheckoutBean.setVisibile(rs.getBoolean("Visibile"));
		 prodottoCarrelloCheckoutBean.setNome(rs.getString("Nome"));
		 prodottoCarrelloCheckoutBean.setDisponibilita(rs.getInt("Disponibilità"));
		 prodottoCarrelloCheckoutBean.setCategoria(rs.getString("Categoria"));
		 prodottoCarrelloCheckoutBean.setIva(rs.getString("Iva"));
		 prodottoCarrelloCheckoutBean.setPrezzo(rs.getDouble("Prezzo"));
		 prodottoCarrelloCheckoutBean.setImgPath(rs.getString("imgPath"));
		 prodottoCarrelloCheckoutBean.setDescrizione(rs.getString("Descrizione"));
		 prodottoCarrelloCheckoutBean.setIdProdotto(rs.getLong("idProdotto"));
		 prodottoCarrelloCheckoutBean.setidProdottoCarrello(rs.getLong("idProdottoCarrello"));
		 prodottoCarrelloCheckoutBean.setIdCarrello(rs.getLong("idCarrello"));
		 prodottoCarrelloCheckoutBean.setQuantita(rs.getInt("Quantita"));

	        return prodottoCarrelloCheckoutBean;
	}
}