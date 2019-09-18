package offers.domain;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class OfferTest {
	
	Offer testOffer;
	
	private static final String TEST_DESCRIPTION = "test description";
	private static final double TEST_PRICE = 1.50;
	private static final List<Product> TEST_PRODUCTS = Lists.newArrayList(
			new Product("Product one", "The first of our products", 2.15),
			new Product("Product two", "The second of our products", 1.52));
	private static final LocalDateTime START_DATE = LocalDateTime.of(2019, 9, 18, 22, 0);
	private static final LocalDateTime END_DATE = LocalDateTime.of(2019, 9, 25, 6, 0);
	
	@Before
	public void setup() {
		testOffer = new Offer(TEST_PRODUCTS, TEST_DESCRIPTION, TEST_PRICE, START_DATE, END_DATE);
	}

	@Test
	public void constructorAndGettersTest() {		
		assertEquals("Products in offer are incorrect", TEST_PRODUCTS, testOffer.getProducts());
		assertEquals("Offer description is incorrect", TEST_DESCRIPTION, testOffer.getDescription());
		assertEquals("Offer price is incorrect", TEST_PRICE, testOffer.getPrice(), 0);
		assertEquals("Offer start date is incorrect", START_DATE, testOffer.getStartDate());
		assertEquals("Offer end date is incorrect", END_DATE, testOffer.getEndDate());
	}
	
	@Test
	public void testSetDescription() {
		String newDescription = "New offer description";
		testOffer.setDescription(newDescription);
		assertEquals("New offer description is incorrect", newDescription, testOffer.getDescription());
	}
	
	@Test
	public void testSetPrice() {
		double newPrice = 1.25;
		testOffer.setPrice(newPrice);
		assertEquals("New offer description is incorrect", newPrice, testOffer.getPrice(), 0);
	}
	
	@Test
	public void testSetProducts() {
		List<Product> newProducts = Lists.newArrayList(
				new Product("new product one", "The first of our new products", 0.80),
				new Product("new product two", "The second of our new products", 1.05),
				new Product("New product three", "The second of our new products", 3.99));
		testOffer.setProducts(newProducts);
		assertEquals("New products in offer are not correct", newProducts, testOffer.getProducts());
	}
	
	@Test
	public void testSetStartDate() {
		LocalDateTime newStartDate = LocalDateTime.of(2019, 10, 1, 0, 0);
		testOffer.setStartDate(newStartDate);
		assertEquals("New offer start date is incorrect", newStartDate, testOffer.getStartDate());
	}
	
	@Test
	public void testSetEndDate() {
		LocalDateTime newEndDate = LocalDateTime.of(2019, 10, 15, 12, 0);
		testOffer.setEndDate(newEndDate);
		assertEquals("New offer end date is incorrect", newEndDate, testOffer.getEndDate());
	}

}
