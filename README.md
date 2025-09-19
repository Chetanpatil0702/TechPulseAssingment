# Demo16 - Spring Boot E-commerce API

A Spring Boot application with JWT-based authentication, role-based access control, and product/category management.

## Features

- **Authentication & Authorization**
  - JWT token-based authentication
  - Role-based access control (RBAC)
  - Permission-based endpoint security

- **User Management**
  - User registration and login
  - Role assignment to users
  - Permission management for roles

- **Product Management**
  - CRUD operations for products
  - Product categorization
  - Paginated product listings

- **Category Management**
  - CRUD operations for categories
  - Category-product relationships

- **API Documentation**
  - Swagger/OpenAPI integration
  - Interactive API documentation

## Tech Stack

- **Backend**: Spring Boot 3.5.5
- **Database**: MySQL 8.0
- **Security**: Spring Security with JWT
- **Documentation**: Swagger/OpenAPI
- **Build Tool**: Maven
- **Java Version**: 21

## Database Schema

- **Users**: User accounts with roles
- **Roles**: User roles with permissions
- **Permissions**: Granular access controls
- **Categories**: Product categories
- **Products**: Products with category relationships

## API Endpoints

### Authentication
- `POST /auth/login` - User login

### Categories
- `POST /api/category/save` - Create category
- `GET /api/category/all` - Get all categories (paginated)
- `GET /api/category/id/{id}` - Get category by ID
- `GET /api/category/{name}` - Get category by name
- `PUT /api/category/update/{id}` - Update category
- `DELETE /api/category/{id}` - Delete category

### Products
- `POST /api/product/add` - Add product
- `GET /api/product/all` - Get all products (paginated)
- `GET /api/product/{id}` - Get product by ID
- `PUT /api/product/update/{id}` - Update product

## Setup Instructions

1. **Prerequisites**
   - Java 21
   - MySQL 8.0
   - Maven

2. **Database Setup**
   - Create MySQL database named `demo16`
   - Update database credentials in `application.properties`

3. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access API Documentation**
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

## Security

- JWT tokens for authentication
- Role-based permissions (e.g., `CATEGORY:CREATE`, `PRODUCT:READ`)
- BCrypt password encoding
- Method-level security with `@PreAuthorize`

## Project Structure

```
src/main/java/com/example/demo16/
├── Configuration/     # Security & Swagger config
├── Controller/        # REST API endpoints
├── Entity/           # JPA entities
├── Service/          # Business logic
├── Repository/       # Data access layer
├── Dto/             # Data transfer objects
└── Exception/       # Global exception handling
```
