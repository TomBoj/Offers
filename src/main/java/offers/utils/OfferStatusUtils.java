package offers.utils;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import offers.domain.Offer;
import offers.domain.OfferStatus;
import offers.repository.OfferRepository;

@Service
public class OfferStatusUtils {
	
	@Autowired
	private OfferRepository offerRepository;
	
	public OfferStatusUtils(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}
	
	@Scheduled(fixedDelay = 5000)
	public void updateOfferStatuses() {
		LocalDateTime currentDate = LocalDateTime.now();
		List<Offer> offers = offerRepository.findAll();
		for (Offer offer : offers) {
			if(offer.getStatus() != OfferStatus.CANCELLED) {
				if(offer.getStartDate().isAfter(currentDate)) {
					offer.setStatus(OfferStatus.PENDING);
				} else if (offer.getEndDate().isBefore(currentDate)) {
					offer.setStatus(OfferStatus.EXPIRED);
				} else {
					offer.setStatus(OfferStatus.VALID);
				}
				offerRepository.save(offer);
			}
		}
	}
	

}
