package bigg.tech.orderservice.service;

import bigg.tech.orderservice.OrderRepository;
import bigg.tech.orderservice.dto.OrderResponseDTO;
import bigg.tech.orderservice.dto.ProductDTO;
import bigg.tech.orderservice.dto.UserDTO;
import bigg.tech.orderservice.feign.ProductServiceClient;
import bigg.tech.orderservice.feign.UserServiceClient;
import bigg.tech.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;

    public OrderResponseDTO createOrder(Long userId, Long productId, Integer quantity) {
        // 1. Kullanıcıyı UserService'den al
        UserDTO user = userServiceClient.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // 2. Ürünü ProductService'den al
        ProductDTO product = productServiceClient.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        // 3. Stok kontrolü
        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        // 4. Order oluştur
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        Order savedOrder = orderRepository.save(order);

        // 5. Response DTO oluştur
        return convertToResponseDTO(savedOrder, user, product);
    }

    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        UserDTO user = userServiceClient.getUserById(order.getUserId());
        ProductDTO product = productServiceClient.getProductById(order.getProductId());

        return convertToResponseDTO(order, user, product);
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    UserDTO user = userServiceClient.getUserById(order.getUserId());
                    ProductDTO product = productServiceClient.getProductById(order.getProductId());
                    return convertToResponseDTO(order, user, product);
                })
                .collect(Collectors.toList());
    }

    private OrderResponseDTO convertToResponseDTO(Order order, UserDTO user, ProductDTO product) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setUser(user);
        dto.setProduct(product);
        dto.setQuantity(order.getQuantity());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        return dto;
    }
}