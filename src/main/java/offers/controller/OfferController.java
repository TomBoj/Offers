package offers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import offers.domain.Offer;
import offers.exception.InvalidDatesException;
import offers.exception.NotFoundException;
import offers.model.OfferDTO;
import offers.service.OfferService;

@CrossOrigin
@RestController
public class OfferController {
	
	@Autowired
	private OfferService offerService;
	
	@GetMapping("/offers")
	public List<Offer> getOffers() {
		return offerService.getAllOffers();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping("/offers")
	public Offer createOffer(@RequestBody OfferDTO offerDto) throws NotFoundException, InvalidDatesException {
		return offerService.createOffer(offerDto);
	}
	
	@GetMapping("/offers/{offerId}")
	public Offer getOffer(@PathVariable Long offerId) throws NotFoundException {
		return offerService.getOffer(offerId);
	}
	
	@PostMapping("/offers/{offerId}/cancel")
	public Offer cancelOffer(@PathVariable Long offerId) throws NotFoundException, InvalidDatesException {
		return offerService.cancelOffer(offerId);
	}

}
