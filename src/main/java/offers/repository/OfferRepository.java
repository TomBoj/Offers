package offers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import offers.domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

}
