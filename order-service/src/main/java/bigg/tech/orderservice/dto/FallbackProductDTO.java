package bigg.tech.orderservice.dto;

import java.math.BigDecimal;

public class FallbackProductDTO extends ProductDTO{
    public FallbackProductDTO(Long productId) {
        super(productId, "Fallback Product", BigDecimal.ZERO, 0);
    }
}
