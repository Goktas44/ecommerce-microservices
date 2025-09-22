package bigg.tech.shippingservice.controller;


import bigg.tech.shippingservice.dto.ShippingResponseDTO;
import bigg.tech.shippingservice.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shippings")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @PostMapping
    public ResponseEntity<ShippingResponseDTO> createShipping(
            @RequestParam Long orderId,
            @RequestParam String address,
            @RequestParam String carrier) {

        ShippingResponseDTO shipping = shippingService.createShipping(orderId, address, carrier);
        return ResponseEntity.ok(shipping);
    }

    @PutMapping("/{shippingId}/status")
    public ResponseEntity<ShippingResponseDTO> updateStatus(
            @PathVariable Long shippingId,
            @RequestParam String status) {

        return ResponseEntity.ok(shippingService.updateShippingStatus(shippingId, status));
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<ShippingResponseDTO> trackShipping(
            @PathVariable String trackingNumber) {

        return ResponseEntity.ok(shippingService.getShippingByTrackingNumber(trackingNumber));
    }

    @GetMapping
    public List<ShippingResponseDTO> getAllShippings() {
        return shippingService.getAllShippings();
    }
}