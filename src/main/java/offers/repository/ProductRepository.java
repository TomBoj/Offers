package offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import offers.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Double> {

}
