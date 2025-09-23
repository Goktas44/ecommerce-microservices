# E-Commerce Microservices Platform

Bu proje, Spring Boot ve Spring Cloud kullanılarak geliştirilmiş modern bir microservices mimarisidir.

## Proje Özeti

E-ticaret işlemlerini microservices mimarisi ile yöneten dağıtık bir sistem. Her servis bağımsız çalışır ve servisler arası iletişim REST API'ler ile sağlanır.

## Sistem Mimarisi

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Client        │───▶│   API Gateway    │───▶│  Service        │
│   (Frontend)    │    │   (Spring Cloud) │    │  Registry       │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                              │
          ┌───────────────────┼───────────────────┐
          │                   │                   │
    ┌─────────────┐     ┌─────────────┐     ┌─────────────┐
    │ User Service│     │Order Service│     │Product      │
    │             │     │             │     │Service      │
    └─────────────┘     └─────────────┘     └─────────────┘
          │                   │                   │
    ┌─────────────┐     ┌─────────────┐
    │  Database   │     │  Database   │     ... Diğer Servisler
    │   (H2)      │     │   (H2)      │
    └─────────────┘     └─────────────┘
```

##  Microservices Listesi

| Servis Adı | Port | Açıklama | Database |
|------------|------|----------|----------|
| Service Registry | 8761 | Servis keşfi (Eureka) | - |
| API Gateway | 8080 | Tek giriş noktası, yönlendirme | - |
| User Service | 8081 | Kullanıcı yönetimi | H2 |
| Product Service | 8082 | Ürün yönetimi | H2 |
| Order Service | 8083 | Sipariş yönetimi | H2 |
| Shipping Service | 8084 | Kargo takip sistemi | H2 |

## 🛠️ Teknoloji Stack'i

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Cloud 2023.0.0**
- **Spring Cloud Gateway** - API Gateway
- **Eureka Server** - Service Discovery
- **OpenFeign** - Servisler arası iletişim
- **Resilience4j** - Circuit Breaker pattern
- **H2 Database** - In-memory database
- **Spring Data JPA** - ORM

##  Özellikler

###  Service Discovery (Eureka)
- Tüm servislerin otomatik kaydı
- Health check monitoring
- Load balancing

###  API Gateway
- Tek merkezi giriş noktası
- Dynamic routing
- Request filtering

###  Service Communication
- Feign Client ile RESTful iletişim
- Declarative client interfaces
- Automatic load balancing

###  Resilience Patterns
- Circuit Breaker pattern
- Retry mekanizması
- Fallback method'lar
- Bulkhead pattern

###  Monitoring
- Spring Boot Actuator
- Health check endpoints
- Metrics monitoring

##  Kurulum ve Çalıştırma

### Ön Gereksinimler
- Java 17
- Maven 3.6+
- IDE (IntelliJ IDEA önerilir)

### Adım Adım Kurulum

1. **Projeyi clone'la**:
```bash
git clone https://github.com/your-username/ecommerce-microservices.git
cd ecommerce-microservices
```

2. **Tüm servisleri build et**:
```bash
mvn clean install
```

3. **Servisleri sırayla çalıştır**:

**Terminal 1 - Service Registry**:
```bash
cd service-registry
mvn spring-boot:run
```

**Terminal 2 - API Gateway**:
```bash
cd api-gateway
mvn spring-boot:run
```

**Terminal 3 - User Service**:
```bash
cd user-service
mvn spring-boot:run
```

**Terminal 4 - Product Service**:
```bash
cd product-service
mvn spring-boot:run
```

**Terminal 5 - Order Service**:
```bash
cd order-service
mvn spring-boot:run
```

**Terminal 6 - Shipping Service**:
```bash
cd shipping-service
mvn spring-boot:run
```

## 🔌 API Endpoints

### User Service
- `GET /api/users` - Tüm kullanıcıları listele
- `GET /api/users/{id}` - ID ile kullanıcı getir
- `POST /api/users` - Yeni kullanıcı oluştur

### Product Service
- `GET /api/products` - Tüm ürünleri listele
- `GET /api/products/{id}` - ID ile ürün getir
- `POST /api/products` - Yeni ürün oluştur

### Order Service
- `POST /api/orders?userId=1&productId=1&quantity=2` - Sipariş oluştur
- `GET /api/orders/{id}` - Sipariş detaylarını getir
- `GET /api/orders` - Tüm siparişleri listele

### Shipping Service
- `POST /api/shippings?orderId=1&address=İstanbul&carrier=Aras` - Kargo oluştur
- `GET /api/shippings/tracking/{trackingNumber}` - Kargo takip
- `PUT /api/shippings/{id}/status?status=SHIPPED` - Kargo durumu güncelle

##  Test Senaryoları

### Senaryo 1: Normal Akış
```bash
# 1. Kullanıcı oluştur
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Ahmet Yılmaz","email":"ahmet@email.com"}'

# 2. Ürün oluştur
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"MacBook Pro","price":25000,"stock":10}'

# 3. Sipariş oluştur
curl -X POST "http://localhost:8080/api/orders?userId=1&productId=1&quantity=1"

# 4. Kargo oluştur
curl -X POST "http://localhost:8080/api/shippings?orderId=1&address=İstanbul&carrier=Aras"
```

### Senaryo 2: Circuit Breaker Testi
```bash
# User Service'i durdur ve order oluşturmaya çalış
# Sistem fallback response dönecek
```

##  Monitoring URLs

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **User Service Health**: http://localhost:8081/actuator/health
- **Product Service Health**: http://localhost:8082/actuator/health
- **Order Service Health**: http://localhost:8083/actuator/health
- **Shipping Service Health**: http://localhost:8084/actuator/health

##  Proje Yapısı

```
ecommerce-microservices/
├── service-registry/          # Eureka Server
├── api-gateway/               # Spring Cloud Gateway
├── user-service/              # Kullanıcı yönetimi
├── product-service/           # Ürün yönetimi
├── order-service/             # Sipariş yönetimi
├── shipping-service/          # Kargo takip
└── pom.xml                    # Parent POM
```

## 🔮 Gelecek Geliştirmeler

- [ ] Docker containerization
- [ ] Kubernetes deployment
- [ ] Redis caching
- [ ] JWT authentication
- [ ] Kafka message queue
- [ ] Elasticsearch logging
- [ ] Prometheus monitoring
- [ ] Grafana dashboard

## 👨‍💻 Geliştirici

Abdurrahman Göktaş - Backend Developer

- Email: goktasabdurrahman44@gmail.com

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır.

---

** Bu projeyi beğendiyseniz yıldız vermeyi unutmayın!**

---

##  Hızlı Başlangıç

Sadece test etmek istiyorsanız:

```bash
# Tüm servisleri tek seferde çalıştır (Maven gerektirir)
mvn -f service-registry/pom.xml spring-boot:run &
mvn -f api-gateway/pom.xml spring-boot:run &
mvn -f user-service/pom.xml spring-boot:run &
mvn -f product-service/pom.xml spring-boot:run &
mvn -f order-service/pom.xml spring-boot:run &
mvn -f shipping-service/pom.xml spring-boot:run &
```
