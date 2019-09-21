package offers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import offers.domain.Offer;
import offers.service.OfferService;

@RestController
public class OfferController {
	
	@Autowired
	private OfferService offerService;
	
	@GetMapping("/offers")
	public List<Offer> getOffers() {
		return offerService.getAllOffers();
	}

}
