# Spring boot library application

This [Spring Boot](http://projects.spring.io/spring-boot/) application is designed to borrow book and keep records of those transactions. It is built using Java with the Spring Framework and MongoDB as the database. 

## Technologies Used

- **Java**
- **Spring Boot**
- **MongoDB**
- **Spring Data MongoDB**
- **Lombok**


## Getting Started

### Prerequisites
For building and running the application you need:

- Java 11 or higher
- Maven
- [MongoDB](https://www.mongodb.com/try/download/community) 

## Installation

1. **Clone the repository** 
2. **Set up MongoDB**
    - **Install and start MongoDB following the [official installation guide](https://www.mongodb.com/docs/manual/installation/).**
3. **Build the application:**
    ```Bash
   mvn clean install
   ```
4. **Run the application:**
    ```Bash 
   mvn spring-boot:run
   ```
## Configuration
The application configuration is located in src/main/resources/application.properties. Adjust the MongoDB connection settings as needed:
```properties
spring.data.mongodb.uri=mongodb://localhost:27017/library
```



### API Documentation
This application uses Swagger for API documentation. Once the application is running, you can access the Swagger UI to explore and test the endpoints:
- Swagger UI: http://localhost:8081/swagger-ui/index.html

### Contact

For any questions or suggestions, please contact [olhovskaya.alena@gmail.com](olhovskaya.alena@gmail.com)