package bigg.tech.orderservice.feign;

import bigg.tech.orderservice.dto.UserDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")//iletişime geçeeği servis
public interface UserServiceClient {
    @GetMapping("/api/users/{userId}")
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")//serviceye ulaşılamazsa getUserfallackmethodunu çağırır
    @Retry(name = "userServiceRetry")
    UserDTO getUserById(@PathVariable("userId") Long userId);

    //fallback method
    default UserDTO getUserFallback(Long userId,Exception e){
        return new UserDTO(userId,"Fallback user","fallback@email.com");
    }
}
