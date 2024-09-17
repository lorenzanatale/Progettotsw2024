package Model.prodottoCarrello;

import java.io.Serializable;
import java.sql.SQLException;
import Model.prodotto.prodottoDAO;
import Model.prodotto.prodottoBean;

public class prodottoCarrelloBean implements Serializable {

	private long id;
	private long idProdotto;
	private long idCarrello;
	private int quantita;
	private String nome;
	private String imgPath;
	private Double prezzo;


	public prodottoCarrelloBean(long id, long idProdotto, long idCarrello, int quantita, String nome, String imgPath, Double prezzo) {
		this.id = id;
		this.idProdotto = idProdotto;
		this.idCarrello = idCarrello;
		this.quantita = quantita;
		this.nome = nome;
		this.imgPath = imgPath;
		this.prezzo = prezzo;
	}

	public prodottoCarrelloBean() {

	}


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
		// Recupera e imposta le informazioni del prodotto
		try (prodottoDAO prodDAO = new prodottoDAO()) {
			prodottoBean prodotto = prodDAO.doRetrieveByKey(idProdotto);
			if (prodotto != null) {
				this.nome = prodotto.getNome();
				this.imgPath = prodotto.getImgPath();
				this.prezzo = prodotto.getPrezzo(); // Assumi che prodottoBean abbia un metodo getPrezzo()
			} else {
				System.out.println("Nessun prodotto trovato con ID: " + idProdotto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore durante il recupero del prodotto: " + e.getMessage(), e);
		}
	}

	public long getIdCarrello() {
		return idCarrello;
	}

	public void setIdCarrello(long idCarrello) {
		this.idCarrello = idCarrello;
	}

	public int getQuantita() {
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

	public double getPrezzo() {
		return prezzo != null ? prezzo : 0.0; // Gestisce il caso in cui prezzo è null
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	@Override
	public String toString() {
		return "prodottoCarrelloBean{" +
				"id=" + id +
				", idProdotto=" + idProdotto +
				", idCarrello=" + idCarrello +
				", quantita=" + quantita +
				", nome='" + nome + '\'' +
				", imgPath='" + imgPath + '\'' +
				", prezzo=" + prezzo +
				'}';
	}
}
