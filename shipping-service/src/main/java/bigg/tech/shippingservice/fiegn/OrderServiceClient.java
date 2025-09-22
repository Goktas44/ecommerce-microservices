package bigg.tech.shippingservice.fiegn;

import bigg.tech.shippingservice.dto.OrderDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FeignClient(name = "order-service")
public interface OrderServiceClient {
    @GetMapping("/api/orders/{orderId}")
    @CircuitBreaker(name = "orderService", fallbackMethod = "getOrderFallback")
    OrderDTO getOrderById(@PathVariable("orderId") Long orderId);

    default OrderDTO getOrderFallback(Long orderId, Exception e) {
        return new OrderDTO(
                orderId,                     // id
                "Fallback User Info",        // userInfo
                "Fallback Product Info",     // productInfo
                0,                          // quantity
                BigDecimal.ZERO,            // totalPrice
                LocalDateTime.now(),        // orderDate
                "FALLBACK"                  // status
        );
    }
}
