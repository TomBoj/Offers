package offers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import offers.domain.Offer;
import offers.domain.OfferStatus;
import offers.domain.Product;
import offers.exception.InvalidDatesException;
import offers.exception.NotFoundException;
import offers.model.OfferDTO;
import offers.repository.OfferRepository;
import offers.repository.ProductRepository;

@Service
public class OfferServiceImpl implements OfferService {
	
	@Autowired
	private OfferRepository offerRepository;
	@Autowired
	private ProductRepository productRepository;
	
	public OfferServiceImpl(OfferRepository offerRepository, ProductRepository productRepository) {
		this.offerRepository = offerRepository;
		this.productRepository = productRepository;
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

	@Override
	public Offer createOffer(OfferDTO offerDto) throws NotFoundException, InvalidDatesException {
		Offer newOffer = new Offer();
		newOffer.setDescription(offerDto.getDescription());
		newOffer.setPrice(offerDto.getPrice());
		if(offerDto.getStartDate().isBefore(offerDto.getEndDate())) {
			newOffer.setStartDate(offerDto.getStartDate());
			newOffer.setEndDate(offerDto.getEndDate());
		} else {
			throw new InvalidDatesException("Offer start date must be earlier than end date");
		}
		List<Product> products = Lists.newArrayList();
		for(long productId : offerDto.getProductIds()) {
			Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", productId)));
			products.add(product);			
		}
		newOffer.setProducts(products);
		return offerRepository.save(newOffer);
	}

	@Override
	public Offer cancelOffer(long id) throws NotFoundException {
		Offer offer = offerRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Offer with id %d not found", id)));
		if(offer.getStatus() != OfferStatus.EXPIRED) {
			offer.setStatus(OfferStatus.CANCELLED);
		}
		return offerRepository.save(offer);
	}

}
