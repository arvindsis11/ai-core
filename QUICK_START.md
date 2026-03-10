# AI Core - Quick Start Guide

## 🚀 Get Running in 5 Minutes

### Option 1: Quick Start with H2 (No Docker)

This is the fastest way to get the application running locally.

```bash
# Navigate to project
cd ai-core

# Build and run
./mvnw clean install
./mvnw spring-boot:run
```

**Application starts at:** `http://localhost:8080/api`

**Test it:**
```bash
curl -X GET "http://localhost:8080/api/v1/ai/ask?question=Hello" \
     -H "Accept: application/json"
```

**Note:** Without OpenAI API key, you'll get a helpful error message.

---

### Option 2: Full Stack with Docker (Recommended for Development)

This sets up PostgreSQL, Redis, and the full enterprise stack.

#### Prerequisites
- Docker & Docker Compose installed
- PowerShell 5.1+
- Java 17+ (for IDE development)

#### Steps

```powershell
# 1. Navigate to project
cd C:\Users\arvind_sisodiya\Documents\1scor-incubation\ai-core

# 2. Start infrastructure (PostgreSQL + Redis)
docker-compose up -d

# 3. Verify services are running
docker-compose ps

# 4. Set OpenAI API key
$env:OPENAI_API_KEY="sk-your-actual-key"

# 5. Run with dev profile
.\mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

**Application starts at:** `http://localhost:8080/api`

#### Cleanup
```powershell
# Stop services
docker-compose down

# Or keep them running and stop the app manually (Ctrl+C in terminal)
```

---

### Option 3: Run Everything in Docker

Complete containerized setup.

```bash
# 1. Build application image
docker build -t ai-core:latest .

# 2. Start with docker-compose (includes all services)
docker-compose up -d
docker run -d \
  --name ai-core-app \
  -p 8080:8080 \
  -e OPENAI_API_KEY="sk-your-key" \
  -e SPRING_PROFILES_ACTIVE=prod \
  --network ai-core-network \
  ai-core:latest

# 3. Check logs
docker logs ai-core-app -f

# 4. Stop
docker-compose down
```

---

## 📊 Verify Installation

### Check Application Health
```bash
curl http://localhost:8080/api/actuator/health
```

Expected response:
```json
{
  "status": "UP"
}
```

### View API Documentation
Open in browser: `http://localhost:8080/api/swagger-ui.html`

### Check Metrics
```bash
curl http://localhost:8080/api/actuator/metrics
```

### Check Database (if using Docker)
- pgAdmin: http://localhost:5050
- Username: `admin@example.com`
- Password: `admin`

---

## 🧪 Test the API

### Test 1: Ask AI a Question
```bash
curl -X GET "http://localhost:8080/api/v1/ai/ask?question=What%20is%20Spring%20Boot" \
     -H "Accept: application/json"
```

### Test 2: Send a Chat Message
```bash
curl -X POST "http://localhost:8080/api/v1/ai/chat" \
     -H "Content-Type: application/json" \
     -d '{
       "message": "Hello! What can you do?",
       "conversationId": "conv-001",
       "userId": "user-001"
     }'
```

### Test 3: Check Error Handling
```bash
curl -X GET "http://localhost:8080/api/v1/ai/ask?question=" \
     -H "Accept: application/json"
```

Expected error response:
```json
{
  "errorCode": "AI_PROVIDER_ERROR",
  "message": "Question cannot be empty",
  "status": 400,
  "timestamp": "2026-03-10T10:30:00",
  "path": "/api/v1/ai/ask",
  "traceId": "550e8400-e29b-41d4-a716-446655440000"
}
```

---

## 🔧 IDE Configuration

### IntelliJ IDEA / JetBrains IDEs

1. **Open Project**
   - File → Open → Select `ai-core` folder
   - Trust project when prompted

2. **Configure Run Configuration**
   - Run → Edit Configurations
   - Click `+` → Application
   - Name: `AI Core (Dev)`
   - Main class: `com.arvind.ai_core.AiCoreApplication`
   - VM options: `-Dspring.profiles.active=dev`
   - Environment variables: `OPENAI_API_KEY=sk-your-key`
   - Working directory: `$ProjectFileDir$`
   - Click OK

3. **Run Application**
   - Click Run button or press Shift+F10
   - Check "Run" tool window for startup logs

### VS Code

1. **Install Extensions**
   - Extension Pack for Java
   - Spring Boot Extension Pack
   - REST Client (optional, for testing)

2. **Debug Configuration** (`.vscode/launch.json`)
   ```json
   {
     "version": "0.2.0",
     "configurations": [
       {
         "type": "java",
         "name": "Spring Boot App",
         "request": "launch",
         "cwd": "${workspaceFolder}",
         "mainClass": "com.arvind.ai_core.AiCoreApplication",
         "projectName": "ai-core",
         "preLaunchTask": "build",
         "args": "--spring.profiles.active=dev"
       }
     ]
   }
   ```

3. **Run**
   - F5 or Debug → Start Debugging
   - Or use Maven: `./mvnw spring-boot:run`

---

## 📚 Key Resources

### Endpoints Quick Reference

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/ai/ask?question=...` | Ask AI a question |
| POST | `/api/v1/ai/chat` | Send chat message |
| GET | `/api/actuator/health` | Health check |
| GET | `/api/actuator/info` | Application info |
| GET | `/api/actuator/metrics` | Metrics list |
| GET | `/api/v3/api-docs` | OpenAPI JSON |
| GET | `/api/swagger-ui.html` | Swagger UI |

### Configuration Files

- **`application.yaml`** - Default configuration (H2 in-memory)
- **`application-dev.yaml`** - Development (PostgreSQL)
- **`application-prod.yaml`** - Production (externalized)
- **`pom.xml`** - Dependencies

### Source Files

- **`AiCoreApplication.java`** - Main entry point
- **`api/v1/controller/AIController.java`** - REST endpoints
- **`service/OpenAIService.java`** - Business logic
- **`service/ai/openai/OpenAIProvider.java`** - AI provider
- **`config/AppConfig.java`** - Core configuration

---

## ⚙️ Environment Variables

### Required
```powershell
$env:OPENAI_API_KEY="sk-your-actual-api-key"
```

### Optional (with defaults)
```powershell
# Database (dev profile)
$env:DB_HOST="localhost"
$env:DB_PORT="5432"
$env:DB_NAME="ai_core_dev"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="postgres"

# Redis (if using)
$env:REDIS_HOST="localhost"
$env:REDIS_PORT="6379"

# Spring
$env:SPRING_PROFILES_ACTIVE="dev"
$env:SERVER_PORT="8080"
```

---

## 🐛 Troubleshooting

### Issue: "Port 8080 already in use"
```powershell
# Find process using port
netstat -ano | findstr :8080

# Kill process (replace <PID>)
taskkill /PID <PID> /F

# Or use different port in application.yaml
# server:
#   port: 8081
```

### Issue: "OpenAI API key not configured"
```powershell
$env:OPENAI_API_KEY="sk-your-key"

# Verify it's set
echo $env:OPENAI_API_KEY
```

### Issue: "Cannot connect to PostgreSQL"
```bash
# Check docker-compose is running
docker-compose ps

# Start if not running
docker-compose up -d

# Check logs
docker-compose logs postgres
```

### Issue: "BUILD FAILURE"
```bash
# Clean and rebuild
./mvnw clean install

# If still failing, check Java version
java -version  # Must be 17+

# Update Maven cache
./mvnw clean install -U
```

### Issue: "H2 Console not available"
When using H2 database, access the console:
```
http://localhost:8080/api/h2-console
```

Spring DataSource URL: `jdbc:h2:mem:testdb;MODE=PostgreSQL`
Username: `sa`
Password: (leave empty)

---

## 📖 Additional Resources

- **Full Documentation**: See `README.md`
- **Implementation Details**: See `IMPLEMENTATION_GUIDE.md`
- **Spring Boot Docs**: https://spring.io/projects/spring-boot
- **OpenAPI Spec**: http://localhost:8080/api/v3/api-docs

---

## ✅ Checklist for First Run

- [ ] Java 17+ installed (`java -version`)
- [ ] Maven 3.9+ or use `./mvnw` wrapper
- [ ] Git cloned the repository
- [ ] Docker & Docker Compose (optional, for full stack)
- [ ] OPENAI_API_KEY environment variable set
- [ ] Application started without errors
- [ ] Swagger UI loads at http://localhost:8080/api/swagger-ui.html
- [ ] Health check passes: http://localhost:8080/api/actuator/health
- [ ] Can call API: `curl http://localhost:8080/api/v1/ai/ask?question=Hello`

---

**Last Updated**: March 10, 2026  
**Status**: Ready for development
