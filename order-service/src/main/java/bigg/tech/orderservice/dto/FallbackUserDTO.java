package bigg.tech.orderservice.dto;

public class FallbackUserDTO extends UserDTO{
    public FallbackUserDTO(Long userId) {
        super(userId, "Fallback User", "fallback@email.com");
    }
}
