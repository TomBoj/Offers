package offers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import offers.domain.Product;
import offers.exception.NotFoundException;
import offers.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProduct(long id) throws NotFoundException {
		return productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(String.format("Product with id %d not found", id)));
	}

}
