package Model.carrello;

import Model.prodottoCarrello.prodottoCarrelloBean;

import java.io.Serializable;
import java.util.List;

public class carrelloBean implements Serializable{
	
	private long id;
	private long idUtente;
	private List<prodottoCarrelloBean> prodotti;
	
	public carrelloBean() {}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getIdUtente() {
		return idUtente;
	}
	
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	
	@Override
	public String toString() {
		return "carrelloBean{" + "id=" + id + ", idUtente=" + idUtente + '}';
	}

	public List<prodottoCarrelloBean> getProdotti(long id) {
		return prodotti;
	}

	public void setProdotti(List<prodottoCarrelloBean> prodotti) {
		this.prodotti = prodotti;
	}


}