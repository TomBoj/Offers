package offers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import offers.domain.Product;
import offers.repository.ProductRepository;

@RestController
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/products")
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

}
