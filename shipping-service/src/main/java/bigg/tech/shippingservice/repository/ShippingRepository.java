package bigg.tech.shippingservice.repository;

import bigg.tech.shippingservice.dto.ShippingResponseDTO;
import bigg.tech.shippingservice.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
    Optional<Shipping> findByTrackingNumber(String trackingNumber);
}
