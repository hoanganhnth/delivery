@echo off

start "api-gateway" cmd /k "cd api-gateway && mvnw spring-boot:run"
start "auth-service" cmd /k "cd auth-service && mvnw spring-boot:run"
start "delivery-service" cmd /k "cd delivery-service && mvnw spring-boot:run"
start "notification-service" cmd /k "cd notification-service && mvnw spring-boot:run"
start "order-service" cmd /k "cd order-service && mvnw spring-boot:run"
start "restaurant-service" cmd /k "cd restaurant-service && mvnw spring-boot:run"
start "search-service" cmd /k "cd search-service && mvnw spring-boot:run"
start "shipper-service" cmd /k "cd shipper-service && mvnw spring-boot:run"
start "user-service" cmd /k "cd user-service && mvnw spring-boot:run"
