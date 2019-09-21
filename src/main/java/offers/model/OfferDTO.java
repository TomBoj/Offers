package offers.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public class OfferDTO {

	private long offerId;
	private List<Long> productIds;
	private String description;
	private double price;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String status;
	
	public long getOfferId() {
		return offerId;
	}

	public List<Long> getProductIds() {
		return productIds;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public String getStatus() {
		return status;
	}
	
}
