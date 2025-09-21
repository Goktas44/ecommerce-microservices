package bigg.tech.orderservice.feign;

import bigg.tech.orderservice.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service") // 👈 Eureka'daki service name
public interface ProductServiceClient {
    @GetMapping("/api/products/{productId}")//bu sayede port değişssede herzaman bulur birden fazla olursa dağıtır
    ProductDTO getProductById(@PathVariable("productId") Long productId);

}
