package com.revature.services;

public abstract class User {
	
	protected Integer id;
	protected String username;
	protected String password;
	protected String type;
	protected String email;
	protected String pending;
	public User(Integer id, String username, String password, String type, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
		this.email = email;
	}
	
	
	public User(Integer id, String username, String password, String type, String email, String pending) {
		this.pending = pending;
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
		this.email = email;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPending() {
		return pending;
	}
	public void setPending(String pending) {
		this.pending = pending;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", type=" + type + ", email="
				+ email + "]";
	}

	
}

