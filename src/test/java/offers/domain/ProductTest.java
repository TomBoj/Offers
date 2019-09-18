package offers.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {
	
	private Product testProduct;
	
	private static final String TEST_NAME = "Test product";
	private static final String TEST_DESCRIPTION = "Product for use in tests";
	private static final double TEST_PRICE = 1.55;
	
	@Before
	public void setup() {
		testProduct = new Product(TEST_NAME, TEST_DESCRIPTION, TEST_PRICE);
	}

	@Test
	public void constructorAndGettersTest() {
		assertEquals("Product name is incorrect", TEST_NAME, testProduct.getName());
		assertEquals("Product description is incorrect", TEST_DESCRIPTION, testProduct.getDescription());
		assertEquals("Product price is incorrect", TEST_PRICE, testProduct.getPrice(), 0);		
	}
	
	@Test 
	public void setNameTest() {
		String newName = "New product name";
		testProduct.setName(newName);
		assertEquals("New product name is incorrect", newName, testProduct.getName());
	}
	
	@Test 
	public void setDescriptionTest() {
		String newDescription = "This is a new description for our product";
		testProduct.setDescription(newDescription);
		assertEquals("New product description is incorrect", newDescription, testProduct.getDescription());
	}
	
	@Test 
	public void setPriceTest() {
		double newPrice = 3.10;
		testProduct.setPrice(newPrice);
		assertEquals("New product name is incorrect", newPrice, testProduct.getPrice(), 0);
	}

}

