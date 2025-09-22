package bigg.tech.orderservice.feign;

import bigg.tech.orderservice.dto.ProductDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service") // 👈 Eureka'daki service name
public interface ProductServiceClient {
    @GetMapping("/api/products/{productId}")//bu sayede port değişssede herzaman bulur birden fazla olursa dağıtır
    @CircuitBreaker(name = "productService", fallbackMethod = "getProductFallback")
    @Retry(name = "productServiceRetry")
    ProductDTO getProductById(@PathVariable("productId") Long productId);

    // Fallback method
    default ProductDTO getProductFallback(Long productId, Exception e) {
        return new ProductDTO(productId, "Fallback Product", java.math.BigDecimal.ZERO, 0);
    }
}
