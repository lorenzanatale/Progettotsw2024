package Model.utente;

import java.io.Serializable;

public class utenteBean implements Serializable {
	
	private long id;
	private String username;
	private String email;
	private String password;
	private boolean isAdmin;
	
	public utenteBean() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	@Override
	public String toString() {
		return "utenteBean{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email + '\''
				+ ", password='" + password + '\'' + ", isAdmin=" + isAdmin + '}';
	}
}
