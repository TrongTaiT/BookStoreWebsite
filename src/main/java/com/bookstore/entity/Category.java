package com.bookstore.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;

@Entity
@NamedQueries({
	@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c ORDER BY c.name"),
	@NamedQuery(name = "Category.countAll", query = "SELECT COUNT(*) FROM Category"),
	@NamedQuery(name = "Category.findByName", query = "SELECT c FROM Category c WHERE c.name = :name")
})
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private int categoryId;

	private String name;

	//bi-directional many-to-one association to Book
	@OneToMany(mappedBy="category")
	private Set<Book> books;

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	public Book addBook(Book book) {
		getBooks().add(book);
		book.setCategory(this);

		return book;
	}

	public Book removeBook(Book book) {
		getBooks().remove(book);
		book.setCategory(null);

		return book;
	}

}