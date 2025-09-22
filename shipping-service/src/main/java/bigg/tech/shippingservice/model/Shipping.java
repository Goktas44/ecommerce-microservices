package bigg.tech.shippingservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "shippings")
@Getter
@Setter
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;           // Order service'den gelen order ID
    private String trackingNumber;  // Kargo takip numarası
    private String address;         // Teslimat adresi
    private String status;          // PREPARING, SHIPPED, DELIVERED, CANCELLED
    private LocalDateTime shippingDate;
    private LocalDateTime estimatedDelivery;
    private String carrier;         // Kargo firması (Aras, Yurtiçi, MNG vb.)

    // constructors, getters, setters
    public Shipping() {}

    public Shipping(Long orderId, String address, String carrier) {
        this.orderId = orderId;
        this.address = address;
        this.carrier = carrier;
        this.status = "PREPARING";
        this.shippingDate = LocalDateTime.now();
        this.estimatedDelivery = LocalDateTime.now().plusDays(3);
        this.trackingNumber = generateTrackingNumber();
    }

    private String generateTrackingNumber() {
        return "TRK" + System.currentTimeMillis();
    }


}
