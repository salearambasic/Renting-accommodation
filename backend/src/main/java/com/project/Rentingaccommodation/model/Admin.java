package com.project.Rentingaccommodation.model;

import javax.persistence.*;

@Entity
@Table(name="administrator")
public class Admin {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="name", columnDefinition="VARCHAR(50)", nullable=false)
	private String name;
	
	@Column(name="surname", columnDefinition="VARCHAR(50)", nullable=false)
	private String surname;
	
	@Column(name="password", columnDefinition="VARCHAR(100)", nullable=false)
	private String password;
	
	@Column(name="email", columnDefinition="VARCHAR(50)", unique=true, nullable=false)
	private String email;
	
	public Admin() {
		
	}

	public Admin(String name, String surname, String password, String email) {
		super();
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Admin [id=" + id + ", name=" + name + ", surname=" + surname + ", password=" + password + ", email="
				+ email + "]";
	}
}
