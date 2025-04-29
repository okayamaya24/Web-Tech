# TCU Sports Management API

This is the backend service for the TCU Sports Management application.

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8.x or higher
- MySQL or PostgreSQL database

### Running the Application
1. Clone the repository
2. Configure database properties in `src/main/resources/application.properties`
3. Run the application using Maven:
   ```
   mvn spring-boot:run
   ```
4. The server will start on port 8080 (or as configured in application.properties)

## API Documentation

The API documentation is available through Swagger UI when the application is running.

### Access Swagger UI
- URL: http://localhost:8080/swagger-ui.html
- This provides an interactive UI to view and test all available API endpoints

### JSON API Documentation
- URL: http://localhost:8080/api-docs
- This returns the OpenAPI specification in JSON format

## Available API Endpoints

The application provides REST APIs for managing:

- Game Schedules (/api/schedules)
- Games (/api/games)
- Crew Members (/api/crew-members)
- Crew Assignments (/api/crew-assignments)
- Availability (/api/availability)
- Authentication (/api/auth)

Refer to the Swagger UI for detailed information on all endpoints, request/response formats, and authentication requirements.

## Security

Some endpoints require admin authentication. Currently, admin access is controlled by providing an admin email as a request parameter.

## License

This project is licensed under the MIT License - see the LICENSE file for details. 