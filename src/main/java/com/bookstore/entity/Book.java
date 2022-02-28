package com.bookstore.entity;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the book database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"), //
		@NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title = :title"), //
		@NamedQuery(name = "Book.findByCategory", query = "SELECT b FROM Book b WHERE b.category.id = :catId"), //
		@NamedQuery(name = "Book.countAll", query = "SELECT COUNT(*) FROM Book b"), //
//		@NamedQuery(name = "Book.findByCategory2", query = "SELECT b FROM Book b "//
//				+ "JOIN Category c ON b.category.categoryId = c.categoryId AND c.categoryId = :catId"), //
})
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private int bookId;

	private String author;

	@Lob
	private String description;

	@Lob
	private byte[] image;

	private String isbn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_time")
	private Date lastUpdateTime;

	private float price;

	@Temporal(TemporalType.DATE)
	@Column(name = "publish_date")
	private Date publishDate;

	private String title;

	// bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	// bi-directional many-to-one association to OrderDetail
	@OneToMany(mappedBy = "book")
	private List<OrderDetail> orderDetails;

	// bi-directional many-to-one association to Review
	@OneToMany(mappedBy = "book")
	private List<Review> reviews;

	// Older Hibernate versions require both getter and setter for a field??
	// 5.4.33 doesn't need this?
//	@Transient
//	private String base64Image;

	public Book() {
	}

	public int getBookId() {
		return this.bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetail addOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().add(orderDetail);
		orderDetail.setBook(this);

		return orderDetail;
	}

	public OrderDetail removeOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().remove(orderDetail);
		orderDetail.setBook(null);

		return orderDetail;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setBook(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setBook(null);

		return review;
	}

	public String getBase64Image() {
		return Base64.getEncoder().encodeToString(this.image);
	}

	// Older Hibernate versions require both getter and setter for a field??
	// 5.4.33 doesn't need this
//	public String getBase64Image() {
//		this.base64Image = Base64.getEncoder().encodeToString(this.image);
//		return this.base64Image;
//	}

	// Older Hibernate versions require both getter and setter for a field??
	// 5.4.33 doesn't need this
//	public void setBase64Image(String base64Image) {
//		this.base64Image = base64Image;
//	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return bookId == other.bookId;
	}

}