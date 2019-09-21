package offers.service;

import java.util.List;

import offers.domain.Offer;
import offers.exception.InvalidDatesException;
import offers.exception.NotFoundException;
import offers.model.OfferDTO;

public interface OfferService {

	List<Offer> getAllOffers();
	
	Offer getOffer(long id) throws NotFoundException;
	
	Offer createOffer(OfferDTO offerDto) throws NotFoundException, InvalidDatesException;
	
}
