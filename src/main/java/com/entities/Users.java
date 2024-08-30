package com.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(length = 15)
	private String name;
	@Column(length = 15)
	private String address;
	private LocalDateTime birthdate;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Books> issuedBook = new ArrayList<>();
	
	
	
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Users(int userId, String name, String address, List<Books> issuedBook, LocalDateTime birthdate) {
		super();
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.issuedBook = issuedBook;
		this.birthdate=birthdate; 	
	}
	public LocalDateTime getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDateTime birthdate) {
		this.birthdate = birthdate;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Books> getIssuedBook() {
		return issuedBook;
	}
	public void setIssuedBook(List<Books> issuedBook) {
		this.issuedBook = issuedBook;
	}
	@Override
	public String toString() {
		return "Users [userId=" + userId + ", name=" + name + ", address=" + address + ", issuedBook=" + issuedBook
				+ "]";
	}
	
	

}
