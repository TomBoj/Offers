package offers.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.google.common.collect.Lists;

import offers.domain.Product;
import offers.exception.NotFoundException;
import offers.repository.ProductRepository;
import offers.service.ProductServiceImpl;

public class ProductServiceImplTest {
	
	@Mock
	private ProductRepository productRepository;
	
	private ProductServiceImpl productService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		productService = spy(new ProductServiceImpl(productRepository));
	}
	
	@Test
	public void getAllProductsTest() {
		Product firstProduct = new Product("Test name 1", "Test description 1", 1.50);
		Product secondProduct = new Product("Test name 2", "Test description 2", 2.50);
		List<Product> returnProducts = Lists.newArrayList(firstProduct, secondProduct);
		doReturn(returnProducts).when(productRepository).findAll();
		
		List<Product> actualProducts = productService.getAllProducts();
		verify(productRepository, times(1)).findAll();
		assertEquals("Actual list of products does not equal expected", returnProducts, actualProducts);
	}
	
	@Test
	public void getAllProductsEmptyTest() {
		List<Product> returnProducts = Lists.newArrayList();
		doReturn(returnProducts).when(productRepository).findAll();
		
		List<Product> actualProducts = productService.getAllProducts();
		verify(productRepository, times(1)).findAll();
		assertEquals("Actual list of should be empty", returnProducts, actualProducts);
	}
	
	@Test
	public void getProductTest() throws Exception {
		long testId = 1;
		Product testProduct = new Product("Test name 1", "Test description 1", 1.50);
		ReflectionTestUtils.setField(testProduct, "productId", testId);
		doReturn(Optional.of(testProduct)).when(productRepository).findById(eq(testId));
		
		Product actualProduct = productService.getProduct(testId);
		verify(productRepository, times(1)).findById(eq(testId));
		assertEquals("Actual product is incorrect", testProduct, actualProduct);
	}
	
	@Test(expected = NotFoundException.class)
	public void getProductNotFoundTest() throws Exception {
		long testId = 1;
		doReturn(Optional.empty()).when(productRepository).findById(eq(testId));
		
		productService.getProduct(testId);
	}

}
