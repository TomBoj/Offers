package offers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import offers.domain.Offer;
import offers.repository.OfferRepository;

@RestController
public class OfferController {
	
	@Autowired
	OfferRepository offerRepositroy;
	
	@GetMapping("/offers")
	public List<Offer> getOffers() {
		return offerRepositroy.findAll();
	}

}
