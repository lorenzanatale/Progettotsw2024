package Model.prodottoOrdine;

import java.io.Serializable;

public class prodottoOrdineBean implements Serializable {
    private long id;
    private String nome;
    private long idProdotto;
    private long idOrdine;
    private double prezzo;
    private int quantita;
    private int iva;
    
    public prodottoOrdineBean() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(long idProdotto) {
		this.idProdotto = idProdotto;
	}

	public long getIdOrdine() {
		return idOrdine;
	}

	public void setIdOrdine(long idOrdine) {
		this.idOrdine = idOrdine;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public int getIva() {
		return iva;
	}

	public void setIva(int iva) {
		this.iva = iva;
	}
    
	@Override
	public String toString() {
		return "prodottoOrdineBean [id=" + id + ", nome=" + nome + ", idProdotto=" + idProdotto + ", idOrdine="
				+ idOrdine + ", prezzo=" + prezzo + ", quantita=" + quantita + ", iva=" + iva + "]";
	}
}
