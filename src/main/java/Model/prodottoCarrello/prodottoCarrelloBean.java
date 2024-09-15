package Model.prodottoCarrello;

import Model.prodotto.prodottoBean;
import Model.prodotto.prodottoDAO;

import java.io.Serializable;
import java.sql.SQLException;

public class prodottoCarrelloBean implements Serializable {

	private long id;
	private long idProdotto;
	private long idCarrello;
	private static int quantita;
	private String nome;
	private prodottoBean prodottoBean;
	private String imgPath;

	public prodottoCarrelloBean() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(long idProdotto) {
		this.idProdotto = idProdotto;

		try (prodottoDAO prodDAO = new prodottoDAO()) {
			this.prodottoBean = prodDAO.doRetrieveByKey(idProdotto);
			if (this.prodottoBean == null) {
				System.out.println("Nessun prodotto trovato con ID: " + idProdotto);
			} else {
				System.out.println("Prodotto trovato: " + prodottoBean.getNome());
				this.nome = prodottoBean.getNome(); // Assicurati di inizializzare il nome
				this.imgPath = prodottoBean.getImgPath(); // Assicurati di inizializzare l'immagine
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore durante il recupero del prodotto: " + e.getMessage(), e);
		}

	}

	public prodottoBean getProdottoBean() {
		return prodottoBean;
	}

	public long getIdCarrello() {
		return idCarrello;
	}

	public void setIdCarrello(long idCarrello) {
		this.idCarrello = idCarrello;
	}

	public static int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	@Override
	public String toString() {
		return "prodottoCarrelloBean{" + "id=" + id + ", idProdotto=" + idProdotto + ", idCarrello=" + idCarrello + ", quantita=" + quantita + ", nome='" + nome + "', imgPath='" + imgPath + "'}";
	}
}