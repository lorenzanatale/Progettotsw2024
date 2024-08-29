package Model.carrello;

import java.io.Serializable;

public class carrelloBean implements Serializable{
	
	private long id;
	private long idUtente;
	
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
}