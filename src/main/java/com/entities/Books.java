package com.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Books")
public class Books {
	
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private int bookId;
	@Column(length = 25)
	private String title;
	@Column(length = 25)
	private String subject;
	@Column(length = 25)
	private String author;
	
	@ManyToOne
    @JoinColumn(name = "userId")
	@JsonBackReference
    private Users user;

	
	
	public Books() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Books(int bookId, String title, String subject, String author, Users user) {
		super();
		this.bookId = bookId;
		this.title = title;
		this.subject = subject;
		this.author = author;
		this.user = user;
	}

	public Books(int bookId) {
		super();
		this.bookId = bookId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Books [bookId=" + bookId + ", title=" + title + ", subject=" + subject + ", author=" + author
				+ ", user=" + user + "]";
	}
	

	
	
}
