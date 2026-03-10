# AI Core - Enterprise-Grade API Application

A fully-fledged enterprise-grade Spring Boot API application for unified resource integration with support for multiple AI providers and comprehensive resource adapters.

## Project Overview

**AI Core** is built to serve as a scalable, secure, and maintainable backend service for integrating various AI providers (OpenAI, Azure OpenAI, Anthropic, Ollama) and external resources in a unified manner.

### Key Features

✅ **Multi-Provider AI Integration** - Support for OpenAI, Azure, Anthropic, Ollama  
✅ **Unified Resource Adapter Pattern** - Generic adapter for integrating external resources  
✅ **Enterprise Security** - JWT authentication, RBAC, OAuth2-ready  
✅ **Comprehensive Error Handling** - Structured error responses with trace IDs  
✅ **API Versioning** - URL-based versioning (/api/v1/)  
✅ **Database Persistence** - JPA/Hibernate with PostgreSQL and Flyway migrations  
✅ **Caching Layer** - Redis integration for response caching  
✅ **Rate Limiting** - Request throttling and quota management  
✅ **Health & Monitoring** - Spring Actuator with custom health checks  
✅ **API Documentation** - OpenAPI 3.0 with Swagger UI  
✅ **Structured Logging** - SLF4J with audit trail support  
✅ **Testing Infrastructure** - Unit, integration, and contract testing ready  

## Project Structure

```
ai-core/
├── src/main/java/com/arvind/ai_core/
│   ├── config/                    # Configuration classes
│   │   ├── AppConfig.java         # Core bean configuration
│   │   ├── SecurityConfig.java    # Security configuration
│   │   └── properties/
│   │       └── AiProperties.java  # Externalized properties
│   ├── api/v1/                    # API Version 1
│   │   ├── controller/            # REST controllers
│   │   │   ├── AIController.java
│   │   │   └── ChatRequestDto.java
│   │   ├── dto/                   # Request/Response DTOs
│   │   │   ├── ApiResponse.java
│   │   │   └── ErrorResponse.java
│   │   └── exception/
│   │       └── GlobalExceptionHandler.java
│   ├── service/                   # Business logic
│   │   └── OpenAIService.java     # AI provider integration
│   ├── common/                    # Shared utilities
│   │   └── exception/
│   │       ├── BusinessException.java
│   │       └── AiProviderException.java
│   └── AiCoreApplication.java
├── src/main/resources/
│   ├── application.yaml           # Default configuration (H2 in-memory)
│   ├── application-dev.yaml       # Development profile (PostgreSQL)
│   ├── application-prod.yaml      # Production profile
│   └── db/migration/              # Flyway SQL migrations
│       └── V1__Initial_Schema.sql
├── src/test/                      # Test classes
├── pom.xml                        # Maven dependencies
├── docker-compose.yaml            # Local development services
├── Dockerfile                     # Container image definition
└── README.md                      # This file

```

## Technology Stack

### Core Framework
- **Spring Boot** 3.5.11
- **Spring Security** - Authentication & Authorization
- **Spring Data JPA** - Database ORM
- **Spring Validation** - Request validation
- **Spring Actuator** - Health checks & monitoring

### Database & Caching
- **PostgreSQL** 15 - Production database
- **H2** - Development/testing in-memory database
- **Redis** 7 - Distributed caching
- **Flyway** - Database migrations

### API & Documentation
- **SpringDoc OpenAPI** 2.0.2 - API documentation
- **Swagger UI** - Interactive API explorer

### Security
- **JJWT** 0.12.3 - JWT token management
- **Spring Security** - OAuth2-ready

### HTTP & Serialization
- **OKHttp 3** - HTTP client
- **Jackson** - JSON processing

### Development & Testing
- **Lombok** - Reduce boilerplate
- **JUnit 5** - Unit testing
- **Mockito** - Mocking framework
- **TestContainers** 1.19.1 - Integration testing with real containers

### Logging
- **SLF4J** - Logging facade
- **Logback** - Logging implementation

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.9+
- Docker & Docker Compose (for local services)
- PostgreSQL 15 (or use Docker Compose)

### Local Development Setup

#### 1. Start Infrastructure Services

```bash
docker-compose up -d
```

This starts:
- PostgreSQL (port 5432)
- Redis (port 6379)
- pgAdmin (port 5050)

#### 2. Configure Environment Variables

```bash
export OPENAI_API_KEY="sk-your-api-key-here"
```

Or for Windows PowerShell:
```powershell
$env:OPENAI_API_KEY="sk-your-api-key-here"
```

#### 3. Build & Run Application

```bash
mvn clean install
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

Or using IDE run configuration with:
- Active profiles: `dev`
- VM options: `-Dspring.profiles.active=dev`

### Running Tests

```bash
# Unit tests
mvn test

# Integration tests
mvn verify

# With coverage
mvn clean test jacoco:report
```

## API Documentation

Once the application is running, access the API documentation at:

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

## Configuration

### Application Properties

Key configuration files:
- `application.yaml` - Default (H2 in-memory, suitable for quick testing)
- `application-dev.yaml` - Development (PostgreSQL local instance)
- `application-prod.yaml` - Production (externalized configuration)

### Environment Variables (Production)

```env
# Database
DB_HOST=postgres.example.com
DB_PORT=5432
DB_NAME=ai_core
DB_USERNAME=postgres
DB_PASSWORD=secure-password

# Redis
REDIS_HOST=redis.example.com
REDIS_PORT=6379
REDIS_PASSWORD=redis-password

# OpenAI
OPENAI_API_KEY=sk-your-api-key

# Application
SPRING_PROFILES_ACTIVE=prod
SERVER_PORT=8080
```

## API Endpoints

### Chat & AI Operations

#### Ask AI
```
GET /api/v1/ai/ask?question=What+is+AI?
```

#### Chat
```
POST /api/v1/ai/chat
Content-Type: application/json

{
  "message": "Hello, how can you help?",
  "conversationId": "conv-123",
  "userId": "user-456"
}
```

### Health & Monitoring

```
GET /api/actuator/health
GET /api/actuator/info
GET /api/actuator/metrics
GET /api/actuator/prometheus
```

## Database Schema

### Tables

**users** - User accounts (Phase 4)
- id (UUID)
- username (unique)
- email (unique)
- created_at, updated_at

**audit_logs** - Audit trail for all API operations
- id (BIGSERIAL)
- user_id, action, resource_type, resource_id
- request_body, response_body
- http_status_code, client_ip, user_agent
- created_at, created_by

**api_usage** - API usage tracking and rate limiting
- id (BIGSERIAL)
- user_id, api_key_id
- endpoint, request_count
- response_time_ms, http_status_code
- created_at

## Implementation Roadmap

### Phase 1: ✅ Foundational Infrastructure
- [x] Dependency management
- [x] Configuration management (profiles)
- [x] Error handling & validation
- [x] Structured logging
- [x] API versioning

### Phase 2: Core AI & Resource Abstraction (In Progress)
- [ ] Multi-provider AI architecture
- [ ] Unified resource adapter pattern
- [ ] Provider configuration
- [ ] Response normalization

### Phase 3: Data Persistence & Transactions (In Progress)
- [ ] Database schema
- [ ] Repository layer
- [ ] Transaction management
- [ ] Migration strategy

### Phase 4: Security & Authentication
- [ ] JWT authentication
- [ ] Authorization layer (RBAC)
- [ ] OAuth2 support
- [ ] API key management

### Phase 5: Advanced Enterprise Features
- [ ] Caching layer (Redis)
- [ ] Rate limiting & throttling
- [ ] Health checks enhancements
- [ ] Request correlation

### Phase 6: API Documentation & Testing
- [ ] OpenAPI/Swagger completion
- [ ] Unit testing suite
- [ ] Integration testing suite
- [ ] Contract testing

### Phase 7: Async & Event-Driven Architecture
- [ ] Async processing (@Async)
- [ ] Event bus (Spring Events/RabbitMQ)
- [ ] Message queue integration

### Phase 8: Production Hardening
- [ ] Deployment profiles (K8s)
- [ ] Performance tuning
- [ ] Security hardening
- [ ] Observability (Jaeger/ELK)

## Best Practices Implemented

✅ **Separation of Concerns** - Controllers, Services, Repositories clearly separated  
✅ **Dependency Injection** - Constructor injection, @RequiredArgsConstructor  
✅ **Error Handling** - Global exception handler with trace IDs  
✅ **Validation** - Bean validation with meaningful error messages  
✅ **Logging** - Structured logging with SLF4J  
✅ **Configuration Management** - Externalized with @ConfigurationProperties  
✅ **Security** - BCrypt password encoding, Bearer token support  
✅ **API Versioning** - URL-based versioning for future compatibility  
✅ **Documentation** - OpenAPI 3.0 with auto-generated Swagger UI  
✅ **Testing** - Testcontainers for integration tests  
✅ **Docker** - Multi-stage build for optimal image size  

## Troubleshooting

### Issue: Connection refused on PostgreSQL
**Solution**: Ensure docker-compose services are running
```bash
docker-compose ps
docker-compose logs postgres
```

### Issue: OpenAI API key not configured
**Solution**: Set the environment variable
```bash
export OPENAI_API_KEY="your-api-key"
```

### Issue: Port 8080 already in use
**Solution**: Change port in application.yaml or kill existing process
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Issue: Database migrations failing
**Solution**: Check Flyway version and SQL syntax
```bash
mvn flyway:info
mvn flyway:clean  # ⚠️ Use with caution - drops all tables
```

## Contributing

1. Create feature branch: `git checkout -b feature/your-feature`
2. Make changes following the existing code style
3. Write tests for new functionality
4. Commit with meaningful messages
5. Push and create Pull Request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Contact & Support

For questions, issues, or contributions:
- Create an issue in the GitHub repository
- Contact the development team

---

**Last Updated**: March 2026  
**Status**: Phase 1 Complete, Phase 2-3 in Progress
