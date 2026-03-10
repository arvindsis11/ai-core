# Multi-stage build for optimal image size
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -DdownloadSources -DdownloadJavadoc
COPY src src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Create non-root user for security
RUN addgroup -g 1000 appuser && adduser -D -u 1000 -G appuser appuser

# Copy built application
COPY --from=builder /app/target/ai-core-*.jar app.jar
RUN chown appuser:appuser app.jar

# Switch to non-root user
USER appuser

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD java -cp app.jar org.springframework.boot.loader.JarLauncher \
    -Dspring.boot.actuate.endpoint.health.enabled=true || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
