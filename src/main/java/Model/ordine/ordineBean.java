package Model.ordine;

import java.io.Serializable;
import java.sql.Date;

public class ordineBean implements Serializable {
	private long id;
	private Date data;
	private long idUtente;
	private long idInfoConsegna;
	
	public ordineBean() {}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public long getIdUtente() {
		return idUtente;
	}
	
	public void setIdUtente(long idUtente) {
		this.idUtente = idUtente;
	}
	
	public long getIdInfoConsegna() {
		return idInfoConsegna;
	}
	
	public void setIdInfoConsegna(long idInfoConsegna) {
		this.idInfoConsegna = idInfoConsegna;
	}
	
    @Override
    public String toString() {
        return "ordineBean{" + "id=" + id + ", idUtente=" + idUtente + ", idInfoConsegna=" + idInfoConsegna + '}';
    }
}
