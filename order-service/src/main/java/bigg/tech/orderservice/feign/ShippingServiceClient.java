package bigg.tech.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "shipping-service")
public interface ShippingServiceClient {

    @PostMapping("/api/shippings")
    String createShipping(
            @RequestParam Long orderId,
            @RequestParam String address,
            @RequestParam String carrier);
}