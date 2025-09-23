package bigg.tech.shippingservice.service;

import bigg.tech.shippingservice.dto.OrderDTO;
import bigg.tech.shippingservice.dto.ShippingResponseDTO;
import bigg.tech.shippingservice.fiegn.OrderServiceClient;
import bigg.tech.shippingservice.model.Shipping;
import bigg.tech.shippingservice.repository.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private OrderServiceClient orderServiceClient;

    //    @CircuitBreaker(name = "shippingService", fallbackMethod = "createShippingFallback")
    public ShippingResponseDTO createShipping(Long orderId, String address, String carrier) {
        // Order'ı kontrol et (Feign Client ile) http isteği atarakm çeker normal rest sistem gibi
        OrderDTO order = orderServiceClient.getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        // Kargo oluştur
        Shipping shipping = new Shipping(orderId, address, carrier);
        Shipping savedShipping = shippingRepository.save(shipping);

        return convertToResponseDTO(savedShipping, order);
    }

    //for service unavailable
//    public ShippingResponseDTO createShippingFallback(Long orderId, String address, String carrier, Exception e) {
//        ShippingResponseDTO fallback = new ShippingResponseDTO();
//        fallback.setOrderId(orderId);
//        fallback.setAddress(address);
//        fallback.setCarrier(carrier);
//        fallback.setStatus("FALLBACK - SERVICE UNAVAILABLE");
//        return fallback;
//    }

    public ShippingResponseDTO updateShippingStatus(Long shippingId, String status) {
        Shipping shipping = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new RuntimeException("Shipping not found"));

        shipping.setStatus(status);
        Shipping updatedShipping = shippingRepository.save(shipping);

        OrderDTO order = orderServiceClient.getOrderById(updatedShipping.getOrderId());

        return convertToResponseDTO(updatedShipping, order);
    }

    public ShippingResponseDTO getShippingByTrackingNumber(String trackingNumber) {
        Shipping shipping = shippingRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Shipping not found"));

        OrderDTO order = orderServiceClient.getOrderById(shipping.getOrderId());

        return convertToResponseDTO(shipping, order);
    }

    public List<ShippingResponseDTO> getAllShippings() {
        return shippingRepository.findAll().stream()
                .map(shipping -> {
                    OrderDTO order = orderServiceClient.getOrderById(shipping.getOrderId());
                    return convertToResponseDTO(shipping, order);
                })
                .collect(Collectors.toList());
    }

    private ShippingResponseDTO convertToResponseDTO(Shipping shipping, OrderDTO order) {
        ShippingResponseDTO dto = new ShippingResponseDTO();
        dto.setId(shipping.getId());
        dto.setOrderId(shipping.getOrderId());
        dto.setTrackingNumber(shipping.getTrackingNumber());
        dto.setAddress(shipping.getAddress());
        dto.setStatus(shipping.getStatus());
        dto.setShippingDate(shipping.getShippingDate());
        dto.setEstimatedDelivery(shipping.getEstimatedDelivery());
        dto.setCarrier(shipping.getCarrier());
        dto.setOrderDetails(order);
        return dto;
    }
}
