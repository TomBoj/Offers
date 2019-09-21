package offers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import offers.domain.Offer;
import offers.exception.NotFoundException;
import offers.repository.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService {
	
	@Autowired
	private OfferRepository offerRepository;
	
	public OfferServiceImpl(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}

	@Override
	public List<Offer> getAllOffers() {
		return offerRepository.findAll();
	}

	@Override
	public Offer getOffer(long id) throws NotFoundException {
		return offerRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Offer with id %d not found", id)));
	}

}
