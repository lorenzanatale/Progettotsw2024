package Model.ordine;

import Model.prodottoOrdine.prodottoOrdineBean;
import java.io.Serializable;
import java.sql.Date;

public class ordineBean extends prodottoOrdineBean implements Serializable {
	private long id;
	private Date data;
	private long idUtente;
	private long idInfoConsegna;
	private double totale; // Campo per il totale dell'ordine

	public ordineBean() {}

	// Getter e setter per ID
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	// Getter e setter per la data
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	// Getter e setter per l'ID dell'utente
	public long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}

	// Getter e setter per l'ID delle informazioni di consegna
	public long getIdInfoConsegna() {
		return idInfoConsegna;
	}

	public void setIdInfoConsegna(long idInfoConsegna) {
		this.idInfoConsegna = idInfoConsegna;
	}

	// Getter e setter per il totale dell'ordine
	public double getTotale() {
		return totale;
	}

	public void setTotale(double totale) {
		this.totale = totale;
	}

	@Override
	public String toString() {
		return "ordineBean{" + "id=" + id + ", idUtente=" + idUtente + ", idInfoConsegna=" + idInfoConsegna + ", totale=" + totale + "}";
	}
}
