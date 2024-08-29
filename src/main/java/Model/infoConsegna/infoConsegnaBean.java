package Model.infoConsegna;

import java.io.Serializable;

public class infoConsegnaBean implements Serializable {
	private long id;
	private long idUtente;
	private String citta;
	private int cap;
	private String via;
	private int numcivico;
	private String destinatario;
	private boolean isDefault;
	
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
	
	public String getCitta() {
		return citta;
	}
	
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	public int getCap() {
		return cap;
	}
	
	public void setCap(int cap) {
		this.cap = cap;
	}
	
	public String getVia() {
		return via;
	}
	
	public void setVia(String via) {
		this.via = via;
	}
	
	public int getNumcivico() {
		return numcivico;
	}
	
	public void setNumcivico(int numcivico) {
		this.numcivico = numcivico;
	}
	
	public String getDestinatario() {
		return destinatario;
	}
	
	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}
	
	public boolean isDefault() {
		return isDefault;
	}
	
	public void setDefault(boolean aDefault) {
		isDefault = aDefault;
	}
	
	@Override
	public String toString() {
		return "infoConsegnaBean{" + "id=" + id + ", idUtente=" + idUtente + ", citta='" + citta + '\'' + ", cap=" + cap
				+ ", via='" + via + '\'' + ", numcivico=" + numcivico + ", destinatario='" + destinatario + '\''
				+ ", isDefault=" + isDefault + '}';
	}
}