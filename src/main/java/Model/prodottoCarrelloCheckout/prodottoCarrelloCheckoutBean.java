package Model.prodottoCarrelloCheckout;

public class prodottoCarrelloCheckoutBean {
    
    private long idProdotto;
    private String nome;
    private String descrizione;
    private int disponibilita;
    private String categoria;
    private String iva;
    private double prezzo;
    private String imgPath;
    private boolean isVisibile;
    private long idProdottoCarrello;
    private long idCarrello;
    private int quantita;

	public prodottoCarrelloCheckoutBean(long idProdotto, String nome, String descrizione, int disponibilita, String categoria, String iva, double prezzo, String imgPath, boolean isVisibile, long idProdottoCarrello, long idCarrello, int quantita) {
		this.idProdotto = idProdotto;
		this.nome = nome;
		this.descrizione = descrizione;
		this.disponibilita = disponibilita;
		this.categoria = categoria;
		this.iva = iva;
		this.prezzo = prezzo;
		this.imgPath = imgPath;
		this.isVisibile = isVisibile;
		this.idProdottoCarrello = idProdottoCarrello;
		this.idCarrello = idCarrello;
		this.quantita = quantita;
	}

	public prodottoCarrelloCheckoutBean() {

	}

	public long getIdProdotto() {
		return idProdotto;
	}
	
	public void setIdProdotto(long idProdotto) {
		this.idProdotto = idProdotto;
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
	
	public void setVisibile(boolean isVisibile) {
		this.isVisibile = isVisibile;
	}
	
	public long getidProdottoCarrello() {
		return idProdottoCarrello;
	}
	
	public void setidProdottoCarrello(long idProdottoCarrello) {
		this.idProdottoCarrello = idProdottoCarrello;
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
	
	@Override
	public String toString() {
		return "prodottoCarrelloCheckoutBean{" + "idProdotto=" + idProdotto + 
				", nome='" + nome + '\'' + ", descrizione='" + descrizione + '\'' +
				", disponibilita=" + disponibilita + ", categoria='" + categoria + '\'' +
				", iva='" + iva + '\'' + ", prezzo=" + prezzo + ", imgPath='" + imgPath + '\'' +
				", isVisibile=" + isVisibile + ", idProdottoCarrello=" + idProdottoCarrello + ", idCarrello=" +
				idCarrello + ", quantita=" + quantita + '}'; 
	}
}