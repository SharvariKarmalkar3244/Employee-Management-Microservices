# Employee Management System - Microservices Architecture

A comprehensive microservices-based Employee Management System built with Spring Boot, Spring Cloud, and MySQL. The system demonstrates service discovery, API gateway, inter-service communication, circuit breaker pattern, and JWT-based authentication.

## 🏗️ Architecture

This project follows a microservices architecture with the following services:

```
┌─────────────────┐
│   API Gateway   │ (Port 9090)
│   (Spring Cloud │
│    Gateway)     │
└────────┬────────┘
         │
         ├──────────────┬──────────────┬──────────────┐
         │              │              │              │
         ▼              ▼              ▼              ▼
┌─────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
│  EMPLOYEE   │ │   ADDRESS   │ │    AUTH     │ │   EUREKA    │
│  Service    │ │  Service    │ │  Service    │ │   Server    │
│  (Port 8081)│ │ (Port 8082) │ │ (Port 8083) │ │ (Port 8761) │
└─────────────┘ └─────────────┘ └─────────────┘ └─────────────┘
       │              │              │
       └──────────────┴──────────────┘
                      │
                      ▼
              ┌─────────────┐
              │   MySQL     │
              │  Database   │
              └─────────────┘
```

## 📋 Services Overview

| Service | Port | Description |
|---------|------|-------------|
| **EUREKA-SERVER** | 8761 | Service Discovery Server (Netflix Eureka) |
| **EMPLOYEE** | 8081 | Employee management service |
| **ADDRESS** | 8082 | Address management service |
| **AUTH** | 8083 | Authentication & JWT token generation |
| **API-GATEWAY** | 9090 | API Gateway with routing, filtering, and circuit breaker |
| **common-lib** | - | Shared library for common entities and exceptions |

## 🛠️ Technologies

- **Java 21**
- **Spring Boot 3.5.16**
- **Spring Cloud 2025.0.0/2025.0.3**
- **Spring Cloud Gateway**
- **Netflix Eureka** (Service Discovery)
- **Spring Cloud OpenFeign** (Declarative REST Client)
- **Resilience4j** (Circuit Breaker)
- **Spring Security** + **JWT** (Authentication)
- **MySQL** (Database)
- **Maven** (Build Tool)
- **ModelMapper** (Object Mapping)

## ✨ Features

- **Service Discovery**: Automatic service registration and discovery using Eureka
- **API Gateway**: Single entry point with routing, filtering, and load balancing
- **Circuit Breaker**: Fault tolerance with Resilience4j and fallback mechanisms
- **JWT Authentication**: Secure token-based authentication
- **Inter-service Communication**: Feign clients for service-to-service communication
- **Centralized Exception Handling**: Common library for consistent error responses
- **Auditable Entities**: Automatic timestamp tracking for created/updated records
- **Custom Error Decoders**: Graceful handling of Feign client errors

## 📦 Prerequisites

- **JDK 21** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)

## 🚀 Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd "MicroServices Projects/Employee Management System"
```

### 2. Database Setup

Create a MySQL database named `employee`:

```sql
CREATE DATABASE employee;
```

Update database credentials in each service's `application.properties` or `application.yml`:

- **EMPLOYEE**: `EMPLOYEE/src/main/resources/application.properties`
- **ADDRESS**: `ADDRESS/src/main/resources/application.properties`
- **AUTH**: `AUTH/src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee
spring.datasource.username=root
spring.datasource.password=admin
```

### 3. Build common-lib

The `common-lib` is a shared library used by EMPLOYEE, ADDRESS, and AUTH services. Build it first:

```bash
cd "MicroServices Projects/common-lib"
mvn clean install
```

### 4. Build All Services

```bash
cd "MicroServices Projects/Employee Management System"
```

Build each service individually:

```bash
cd EUREKA-SERVER && mvn clean install && cd ..
cd EMPLOYEE && mvn clean install && cd ..
cd ADDRESS && mvn clean install && cd ..
cd AUTH && mvn clean install && cd ..
cd API-GATEWAY && mvn clean install && cd ..
```

## 🏃 Running the Application

### Startup Order

Start services in the following order:

1. **EUREKA-SERVER** (Service Discovery)
2. **EMPLOYEE** (Employee Service)
3. **ADDRESS** (Address Service)
4. **AUTH** (Authentication Service)
5. **API-GATEWAY** (API Gateway)

### Option 1: Run from IDE

Run each service's main class:
- `EUREKA-SERVER/src/main/java/com/eureka_server/EurekaServerApplication.java`
- `EMPLOYEE/src/main/java/com/employee/EmployeeApplication.java`
- `ADDRESS/src/main/java/com/address/AddressApplication.java`
- `AUTH/src/main/java/com/auth/AuthApplication.java`
- `API-GATEWAY/src/main/java/com/apiGateway/ApiGatwayApplication.java`

### Option 2: Run with Maven

```bash
# EUREKA-SERVER
cd EUREKA-SERVER && mvn spring-boot:run

# EMPLOYEE (in new terminal)
cd EMPLOYEE && mvn spring-boot:run

# ADDRESS (in new terminal)
cd ADDRESS && mvn spring-boot:run

# AUTH (in new terminal)
cd AUTH && mvn spring-boot:run

# API-GATEWAY (in new terminal)
cd API-GATEWAY && mvn spring-boot:run
```

### Option 3: Run with JAR

```bash
# Build all services first
cd EUREKA-SERVER && mvn clean package && cd ..
cd EMPLOYEE && mvn clean package && cd ..
cd ADDRESS && mvn clean package && cd ..
cd AUTH && mvn clean package && cd ..
cd API-GATEWAY && mvn clean package && cd ..

# Run services
java -jar EUREKA-SERVER/target/EUREKA-SERVER-0.0.1-SNAPSHOT.jar
java -jar EMPLOYEE/target/EMPLOYEE-0.0.1-SNAPSHOT.jar
java -jar ADDRESS/target/ADDRESS-0.0.1-SNAPSHOT.jar
java -jar AUTH/target/AUTH-0.0.1-SNAPSHOT.jar
java -jar API-GATEWAY/target/API-GATEWAY-0.0.1-SNAPSHOT.jar
```

## 🔌 API Endpoints

### Base URL
All requests should go through the API Gateway: `http://localhost:9090`

### Authentication Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/login` | Login and get JWT token |
| POST | `/auth/register` | Register new user |

### Employee Endpoints

| Method | Endpoint | Description | Headers |
|--------|----------|-------------|---------|
| POST | `/employees` | Create employee | `Authorization: Bearer <token>` |
| GET | `/employees` | Get all employees | `Authorization: Bearer <token>` |
| GET | `/employees/{id}` | Get employee by ID | `Authorization: Bearer <token>` |
| PUT | `/employees/{id}` | Update employee | `Authorization: Bearer <token>` |
| DELETE | `/employees/{id}` | Delete employee | `Authorization: Bearer <token>` |
| GET | `/employees/{id}/addresses` | Get addresses for employee | `Authorization: Bearer <token>` |

### Address Endpoints

| Method | Endpoint | Description | Headers |
|--------|----------|-------------|---------|
| POST | `/addresses/save` | Save address | `Authorization: Bearer <token>` |
| PUT | `/addresses/update` | Update address | `Authorization: Bearer <token>` |
| GET | `/addresses/employee/{empId}` | Get addresses by employee ID | `Authorization: Bearer <token>` |
| GET | `/addresses/{id}` | Get address by ID | `Authorization: Bearer <token>` |
| GET | `/addresses` | Get all addresses | `Authorization: Bearer <token>` |
| DELETE | `/addresses/{id}` | Delete address | `Authorization: Bearer <token>` |

### Service Discovery

- **Eureka Dashboard**: `http://localhost:8761`

### Health & Monitoring

- **Gateway Health**: `http://localhost:9090/actuator/health`
- **Circuit Breakers**: `http://localhost:9090/actuator/circuitbreakers`

## 📝 Example API Usage

### 1. Register User

```bash
curl -X POST http://localhost:9090/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123",
    "email": "admin@example.com"
  }'
```

### 2. Login

```bash
curl -X POST http://localhost:9090/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "admin123"
  }'
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Create Employee

```bash
curl -X POST http://localhost:9090/employees \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "empName": "John Doe",
    "empEmail": "john@example.com",
    "empCode": "EMP001",
    "companyName": "Tech Corp"
  }'
```

### 4. Save Address

```bash
curl -X POST http://localhost:9090/addresses/save \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "empId": 1,
    "addressRequestDto": [
      {
        "street": "123 Main St",
        "city": "New York",
        "state": "NY",
        "pinCode": "10001",
        "country": "USA",
        "addressType": "HOME"
      }
    ]
  }'
```

## 🔧 Configuration

### Service Configuration Files

- **EUREKA-SERVER**: `EUREKA-SERVER/src/main/resources/application.properties`
- **EMPLOYEE**: `EMPLOYEE/src/main/resources/application.properties`
- **ADDRESS**: `ADDRESS/src/main/resources/application.properties`
- **AUTH**: `AUTH/src/main/resources/application.properties`
- **API-GATEWAY**: `API-GATEWAY/src/main/resources/application.yml`

### Key Configuration Properties

#### Eureka Server
```properties
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

#### API Gateway Circuit Breaker
```yaml
resilience4j:
  circuitbreaker:
    instances:
      EMPLOYEE-SERVICE:
        failureRateThreshold: 50
        waitDurationInOpenState: 6s
        slidingWindowSize: 5
      ADDRESS-SERVICE:
        failureRateThreshold: 50
        waitDurationInOpenState: 6s
        slidingWindowSize: 5
```

## 🧪 Testing

### Run Tests

```bash
cd <service-directory>
mvn test
```

### Test All Services

```bash
mvn test -pl EUREKA-SERVER,EMPLOYEE,ADDRESS,AUTH,API-GATEWAY
```

## 🔍 Troubleshooting

### Common Issues

1. **Connection Refused Error**
   - Ensure EUREKA-SERVER is running before starting other services
   - Check if services are registered in Eureka Dashboard at `http://localhost:8761`

2. **Database Connection Error**
   - Verify MySQL is running
   - Check database credentials in `application.properties`
   - Ensure database `employee` exists

3. **JWT Token Invalid**
   - Ensure you're using a valid token from `/auth/login`
   - Check token expiration time

4. **Circuit Breaker Open**
   - Circuit breaker opens when failure rate exceeds threshold (50%)
   - Wait for `waitDurationInOpenState` (6s) for automatic recovery
   - Check circuit breaker status at `/actuator/circuitbreakers`

5. **Port Already in Use**
   - Change port in respective `application.properties` or `application.yml`
   - Kill process using the port: `netstat -ano | findstr <port>`

## 📚 Project Structure

```
Employee Management System/
├── EUREKA-SERVER/          # Service Discovery Server
├── EMPLOYEE/               # Employee Management Service
├── ADDRESS/                # Address Management Service
├── AUTH/                   # Authentication Service
├── API-GATEWAY/            # API Gateway
└── common-lib/             # Shared Library
    ├── entity/             # Common entities (AuditableEntity)
    └── exception/          # Common exceptions and handlers
```

## 🔐 Security

- JWT-based authentication
- API Gateway filter for token validation
- Spring Security configuration in AUTH service
- Password encoding with BCrypt

## 🔄 Circuit Breaker & Fallback

The API Gateway implements circuit breaker pattern with Resilience4j:

- **Fallback Endpoints**:
  - `/employeeServiceFallback` - Returns message when EMPLOYEE service is down
  - `/addressServiceFallback` - Returns message when ADDRESS service is down

- **Configuration**:
  - Failure rate threshold: 50%
  - Wait duration in open state: 6s
  - Sliding window size: 5 calls

## 📊 Monitoring

- **Eureka Dashboard**: `http://localhost:8761` - View registered services
- **Actuator Endpoints**: `http://localhost:9090/actuator` - Health, metrics, circuit breakers

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

- **Your Name** - Initial work

## 🙏 Acknowledgments

- Spring Boot & Spring Cloud documentation
- Netflix Eureka & Resilience4j communities
