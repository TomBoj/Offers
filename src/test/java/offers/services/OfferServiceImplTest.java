package offers.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;

import offers.domain.Offer;
import offers.domain.Product;
import offers.exception.InvalidDatesException;
import offers.exception.NotFoundException;
import offers.model.OfferDTO;
import offers.repository.OfferRepository;
import offers.repository.ProductRepository;
import offers.service.OfferServiceImpl;

public class OfferServiceImplTest {

	@Mock
	private OfferRepository offerRepository;
	@Mock
	private ProductRepository productRepository;
	
	private OfferServiceImpl offerService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		offerService = spy(new OfferServiceImpl(offerRepository, productRepository));
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
	
	@Test
	public void createOfferTest() throws NotFoundException, InvalidDatesException {
		String testDescription = "test description";
		double testPrice = 2.99;
		List<Long> productIds = Lists.newArrayList(11L, 12L);
		LocalDateTime testStartDate = LocalDateTime.of(2019, 9, 21, 0, 1);
		LocalDateTime testEndDate = LocalDateTime.of(2019, 9, 28, 23, 59);
		OfferDTO offerDto = OfferDTO.builder()
				.price(testPrice)
				.productIds(productIds)
				.description(testDescription)
				.startDate(testStartDate)
				.endDate(testEndDate)
				.build();
		Product product1 = new Product("Product 1", "Test product 1", 1.99);
		Product product2 = new Product("Product 2", "Test product 2", 2.50);
		List<Product> products = Lists.newArrayList(product1, product2);
		
		Offer returnOffer = new Offer(Lists.newArrayList(), 
				testDescription, testPrice, testStartDate, testEndDate);		
		
		doReturn(Optional.of(product1)).when(productRepository).findById(eq(11L));
		doReturn(Optional.of(product2)).when(productRepository).findById(eq(12L));
		doReturn(returnOffer).when(offerRepository).save(any(Offer.class));
		offerService.createOffer(offerDto);
		
		ArgumentCaptor<Offer> offerCaptor = ArgumentCaptor.forClass(Offer.class);
		verify(offerRepository, times(1)).save(offerCaptor.capture());
		
		Offer actualOffer = offerCaptor.getValue();
		assertEquals("Offer description is incorrect", testDescription, actualOffer.getDescription());
		assertEquals("Offer price is incorrect", testPrice, actualOffer.getPrice(), 0);
		assertEquals("Offer products list is incorrect", products, actualOffer.getProducts());
		assertEquals("Offer start date is incorrect", testStartDate, actualOffer.getStartDate());
		assertEquals("Offer end date is incorrect", testEndDate, actualOffer.getEndDate());
	}
	
	@Test(expected = NotFoundException.class)
	public void createOfferProductNotFoundTest() throws NotFoundException,InvalidDatesException {
		String testDescription = "test description";
		double testPrice = 2.99;
		List<Long> productIds = Lists.newArrayList(11L, 12L);
		LocalDateTime testStartDate = LocalDateTime.of(2019, 9, 21, 0, 1);
		LocalDateTime testEndDate = LocalDateTime.of(2019, 9, 28, 23, 59);
		OfferDTO offerDto = OfferDTO.builder()
				.price(testPrice)
				.productIds(productIds)
				.description(testDescription)
				.startDate(testStartDate)
				.endDate(testEndDate)
				.build();
	
		doReturn(Optional.empty()).when(productRepository).findById(eq(11L));
		offerService.createOffer(offerDto);		
	}
	
	@Test(expected = InvalidDatesException.class)
	public void createOfferInvalidDatesTest() throws NotFoundException, InvalidDatesException {
		String testDescription = "test description";
		double testPrice = 2.99;
		List<Long> productIds = Lists.newArrayList(11L, 12L);
		LocalDateTime testStartDate = LocalDateTime.of(2019, 9, 21, 0, 1);
		LocalDateTime testEndDate = LocalDateTime.of(2019, 9, 14, 23, 59);
		OfferDTO offerDto = OfferDTO.builder()
				.price(testPrice)
				.productIds(productIds)
				.description(testDescription)
				.startDate(testStartDate)
				.endDate(testEndDate)
				.build();
	
		offerService.createOffer(offerDto);		
	}
	
}
