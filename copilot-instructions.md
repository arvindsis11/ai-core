# GitHub Copilot Instructions for a Production-Quality Spring Boot 3+ Backend Application

## Overview
This document outlines comprehensive guidelines for using GitHub Copilot effectively in developing a Spring Boot 3+ backend application, adhering to Clean Architecture principles, alongside best practices for AI integration, API design standards, and enterprise-grade coding practices.

## Clean Architecture Principles
1. **Separation of Concerns**: Organize the code into layers (presentation, application, domain, infrastructure) to separate business logic from frameworks and tools.
2. **Dependency Rule**: Source code dependencies should only point inwards. Higher-level modules should not depend on lower-level modules.
3. **Framework Independence**: The architecture should not depend on specific frameworks to ensure adaptability and longevity of the codebase.
4. **Testable**: The design should facilitate easy testing of components in isolation.

## GitHub Copilot Integration in Spring Boot
### AI Integration Guidelines
- Utilize Copilot suggestions to generate boilerplate code such as controllers, services, and repositories.
- Ask Copilot for comments/documentation in your code. For example, you can type `///` above a method to get descriptive comments suggesting possible parameter explanations and return values.
- Use Copilot to implement security configurations by typing prompts related to JWT authentication, CORS settings, etc.

### API Design Standards
1. **RESTful Principles**: Follow REST rules for resource naming, HTTP methods, and status codes.
   - Use nouns for endpoints (e.g., `/users` for user resource).
   - Implement standard HTTP methods (GET, POST, PUT, DELETE).
2. **Versioning**: Always version your API (e.g., `/api/v1/users`).
3. **Response Consistency**: Structure your responses in a standardized manner (preferably JSON) and always return appropriate status codes.
4. **Input Validation**: Ensure all inputs are validated and sanitized to avoid security vulnerabilities.

### Enterprise-Grade Coding Practices
- **Code Reviews**: Always conduct thorough code reviews utilizing GitHub PRs to maintain code quality and standards.
- **Documentation**: Maintain updated documentation using tools like Swagger for API specs and Markdown files for code.
- **Error Handling**: Implement global exception handling using `@ControllerAdvice` to ensure uniform error responses.
- **Logging**: Utilize SLF4J with Logback for logging rather than System.out to effectively manage log levels and file outputs.

## Conclusion
Utilizing GitHub Copilot can accelerate development by providing tailored code suggestions and documentation. By adhering to the aforementioned guidelines and principles, developers can ensure the creation of a robust, maintainable, and scalable Spring Boot application that meets enterprise standards.
