package offers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import offers.domain.Product;
import offers.exception.NotFoundException;

public interface ProductService {

	List<Product> getAllProducts();
	
	Product getProduct(long id) throws NotFoundException;
	
}
