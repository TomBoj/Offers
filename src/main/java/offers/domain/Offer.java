package offers.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity
public class Offer {
	
	@Id
	@GeneratedValue
	long offerId;
	
	@ManyToMany
	@JoinTable (name="OFFER_PRODUCT",
		joinColumns = @JoinColumn(name = "OFFER_FK", referencedColumnName = "offerId"),
		inverseJoinColumns = @JoinColumn(name = "PRODUCT_FK", referencedColumnName = "productId"))
	private List<Product> products;
	private String description;
	private double price;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	public Offer(List<Product> products, String description, double price, LocalDateTime startDate, LocalDateTime endDate) {
		this.products = products;
		this.description = description;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public long getOfferId() {
		return offerId;
	}
	
	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;		
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

}
