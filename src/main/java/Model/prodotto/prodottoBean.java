package Model.prodotto;

import java.io.Serializable;

public class prodottoBean implements Serializable {
	private long id;
	private String nome;
	private String descrizione;
	private int disponibilita;
	private String categoria;
	private String iva;
	private double prezzo;
	private String imgPath;
	private boolean isVisibile;
	
	public prodottoBean() {}
	
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
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public int getDisponibilita() {
		return disponibilita;
	}
	
	public void setDisponibilita(int disponibilita) {
		this.disponibilita = disponibilita;
	}
	
	public String getCategoria() {
		return categoria;
	}
	
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getIva() {
		return iva;
	}
	
	public void setIva(String iva) {
		this.iva = iva;
	}
	
	public double getPrezzo() {
		return prezzo;
	}
	
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
	public String getImgPath() {
		return imgPath;
	}
	
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public boolean isVisibile() {
		return isVisibile;
	}
	
	public void setVisibile(boolean visibile) {
		isVisibile = visibile;
	}
	
	public double getPrezzoConIva() {
		return getPrezzo() + (getPrezzo() * Double.parseDouble(getIva()) /100);
	}
	
	@Override
	public String toString() {
		return "prodottoBean{" + "id=" + id + ", nome='" + nome + '\'' + ", descrizione='" + descrizione + '\''
				+ ", disponibilita=" + disponibilita + ", categoria='" + categoria + '\'' + ", iva='" + iva + '\''
				+ ", prezzo=" + prezzo + ", imgPath='" + imgPath + '\'' + '}';
	}
}
