package offers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import offers.domain.Offer;
import offers.exception.NotFoundException;

public interface OfferService {

	List<Offer> getAllOffers();
	
	Offer getOffer(long id) throws NotFoundException;
	
}
