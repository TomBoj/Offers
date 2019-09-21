package offers.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;

import offers.domain.Offer;
import offers.domain.Product;
import offers.exception.NotFoundException;
import offers.repository.OfferRepository;
import offers.service.OfferServiceImpl;

public class OfferServiceImplTest {

	@Mock
	private OfferRepository offerRepository;
	
	private OfferServiceImpl offerService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		offerService = spy(new OfferServiceImpl(offerRepository));
	}
	
	@Test
	public void getAllOffersTest() {
		List<Product> returnProducts1 = Lists.newArrayList(
				new Product("Test name 1", "Test description 1", 1.50),
				new Product("Test name 2", "Test description 2", 2.50));
		Offer offer1 = new Offer(returnProducts1, "Offer 1", 3.50,
				LocalDateTime.of(2019, 10, 5, 0, 0), LocalDateTime.of(2019, 10, 12, 23, 59));
		List<Product> returnProducts2 = Lists.newArrayList(
				new Product("Test name 3", "Test description 3", 0.75),
				new Product("Test name 4", "Test description 4", 3.99));
		Offer offer2 = new Offer(returnProducts2, "Offer 2", 4.25,
				LocalDateTime.of(2019, 11, 1, 0, 1), LocalDateTime.of(2019, 11, 15, 12, 30));
		
		List<Offer> returnOffers = Lists.newArrayList(offer1, offer2);
		doReturn(returnOffers).when(offerRepository).findAll();
		
		List<Offer> actualOffers = offerService.getAllOffers();
		verify(offerRepository, times(1)).findAll();
		assertEquals("Actual list of offers does not equal expected", returnOffers, actualOffers);
	}
	
	@Test
	public void getAllOffersEmptyTest() {
		List<Offer> returnOffers = Lists.newArrayList();
		doReturn(returnOffers).when(offerRepository).findAll();
		
		List<Offer> actualOffers = offerService.getAllOffers();
		verify(offerRepository, times(1)).findAll();
		assertEquals("Actual list of should be empty", returnOffers, actualOffers);
	}
	
	@Test
	public void getOfferTest() throws Exception {
		long testId = 0;
		Product firstProduct = new Product("Test name 1", "Test description 1", 1.50);
		Product secondProduct = new Product("Test name 2", "Test description 2", 2.50);
		List<Product> returnProducts = Lists.newArrayList(firstProduct, secondProduct);
		Offer testOffer = new Offer(returnProducts, "Test offer", 3.50,
				LocalDateTime.of(2019, 10, 5, 0, 0), LocalDateTime.of(2019, 10, 12, 23, 59));
		doReturn(Optional.of(testOffer)).when(offerRepository).findById(eq(testId));
		
		Offer actualOffer = offerService.getOffer(testId);
		verify(offerRepository, times(1)).findById(eq(testId));
		assertEquals("Actual offer is incorrect", testOffer, actualOffer);
	}
	
	@Test(expected = NotFoundException.class)
	public void getOfferNotFoundTest() throws Exception {
		long testId = 1;
		doReturn(Optional.empty()).when(offerRepository).findById(eq(testId));
		
		offerService.getOffer(testId);
	}
	
}
