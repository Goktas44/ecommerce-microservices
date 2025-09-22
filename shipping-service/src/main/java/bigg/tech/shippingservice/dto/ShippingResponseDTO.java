package bigg.tech.shippingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingResponseDTO {
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private String address;
    private String status;
    private LocalDateTime shippingDate;
    private LocalDateTime estimatedDelivery;
    private String carrier;
    private OrderDTO orderDetails;

    // constructors, getters, setters
}
