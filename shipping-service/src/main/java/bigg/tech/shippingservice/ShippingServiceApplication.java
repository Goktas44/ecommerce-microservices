package bigg.tech.shippingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient//bunun eurek tarafından seçilmesini sağlar(eğre bir yerden veri veriyorsanız falan kullanılır.)
@EnableFeignClients//feingn client kısmında gerekli olan çekilir.(çekilmesini sağlar)(veri diğer serverelardan çekiyorsa)
public class ShippingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingServiceApplication.class, args);
    }

}
