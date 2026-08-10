# ShopSphere

ShopSphere is a backend e-commerce REST API built with Java and Spring Boot.

The project was developed as a learning project to understand the fundamentals of building a real-world Spring Boot backend, including REST APIs, JPA/Hibernate, DTOs, validation, exception handling, database relationships, and transactional operations.

## Features

### User Management

* Create a user
* Get user by ID
* Update user
* Delete user
* Prevent duplicate email registration
* Password encryption using BCrypt

### Category Management

* Create category
* Get all categories
* Get category by ID
* Update category
* Delete category
* Prevent duplicate category names

### Product Management

* Create product
* Get all products
* Get product by ID
* Update product
* Delete product
* Associate products with categories
* Prevent duplicate product names
* Maintain product stock

### Cart Management

* Create a cart for a user
* Add products to cart
* Update product quantity
* Remove products from cart
* View cart
* Prevent multiple carts for the same user
* Validate available product stock

### Order Management

* Place an order from the user's cart
* Automatically calculate the order total
* Create order items from cart items
* Check product stock before placing an order
* Reduce product stock after a successful order
* Automatically clear the cart after placing an order
* Get an order by ID
* Get all orders belonging to a user
* Update order status

---

## Technology Stack

| Technology        | Purpose               |
| ----------------- | --------------------- |
| Java              | Programming language  |
| Spring Boot       | Backend framework     |
| Spring Web        | REST API development  |
| Spring Data JPA   | Database interaction  |
| Hibernate         | ORM                   |
| Spring Validation | Request validation    |
| Spring Security   | Password encryption   |
| BCrypt            | Password hashing      |
| MySQL             | Relational database   |
| Maven             | Dependency management |
| Postman           | API testing           |
| Git & GitHub      | Version control       |

---

## Project Structure

```text
src
└── main
    ├── java
    │   └── com.shopsphere
    │       ├── controller
    │       ├── dto
    │       ├── entity
    │       ├── exception
    │       ├── repository
    │       ├── service
    │       └── ShopSphereApplication.java
    │
    └── resources
        └── application.properties
```

The application follows a layered architecture:

```text
Client
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
Database
```

---

## Entity Relationships

The main entities are:

```text
User
├── Cart
│   └── CartItem
│       └── Product
│
└── Order
    └── OrderItem
        └── Product

Category
└── Product
```

### Relationships

* One User → One Cart
* One User → Many Orders
* One Category → Many Products
* One Cart → Many CartItems
* One Product → Many CartItems
* One Order → Many OrderItems
* One Product → Many OrderItems

---

## Order Flow

When a user places an order, the application follows this flow:

```text
User
  |
  v
Cart
  |
  v
CartItems
  |
  v
Check Product Stock
  |
  v
Calculate Total
  |
  v
Create Order
  |
  v
Create OrderItems
  |
  v
Reduce Product Stock
  |
  v
Clear Cart
```

The complete operation is handled inside a transaction using `@Transactional`.

---

# API Endpoints

## User Endpoints

| Method | Endpoint          | Description |
| ------ | ----------------- | ----------- |
| POST   | `/api/users`      | Create user |
| GET    | `/api/users/{id}` | Get user    |
| PUT    | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

## Category Endpoints

| Method | Endpoint               | Description        |
| ------ | ---------------------- | ------------------ |
| POST   | `/api/categories`      | Create category    |
| GET    | `/api/categories`      | Get all categories |
| GET    | `/api/categories/{id}` | Get category       |
| PUT    | `/api/categories/{id}` | Update category    |
| DELETE | `/api/categories/{id}` | Delete category    |

## Product Endpoints

| Method | Endpoint             | Description      |
| ------ | -------------------- | ---------------- |
| POST   | `/api/products`      | Create product   |
| GET    | `/api/products`      | Get all products |
| GET    | `/api/products/{id}` | Get product      |
| PUT    | `/api/products/{id}` | Update product   |
| DELETE | `/api/products/{id}` | Delete product   |

## Cart Endpoints

| Method | Endpoint                                | Description         |
| ------ | --------------------------------------- | ------------------- |
| POST   | `/api/carts`                            | Create cart         |
| GET    | `/api/carts/{id}`                       | Get cart            |
| POST   | `/api/carts/{cartId}/items`             | Add product to cart |
| PUT    | `/api/carts/{cartId}/items/{productId}` | Update quantity     |
| DELETE | `/api/carts/{cartId}/items/{productId}` | Remove product      |

## Order Endpoints

| Method | Endpoint                       | Description         |
| ------ | ------------------------------ | ------------------- |
| POST   | `/api/orders`                  | Place order         |
| GET    | `/api/orders/{id}`             | Get order           |
| GET    | `/api/orders/user/{userId}`    | Get user's orders   |
| PUT    | `/api/orders/{orderId}/status` | Update order status |

---

# Example Requests

## Create User

```http
POST /api/users
Content-Type: application/json
```

```json
{
  "name": "Priyansh",
  "email": "priyansh@example.com",
  "password": "password123"
}
```

## Create Category

```http
POST /api/categories
Content-Type: application/json
```

```json
{
  "name": "Electronics",
  "description": "Electronic products"
}
```

## Create Product

```http
POST /api/products
Content-Type: application/json
```

```json
{
  "name": "Wireless Mouse",
  "description": "2.4GHz wireless mouse",
  "price": 999.00,
  "stock": 20,
  "categoryId": 1
}
```

## Create Cart

```http
POST /api/carts
Content-Type: application/json
```

```json
{
  "userId": 1
}
```

## Place Order

```http
POST /api/orders
Content-Type: application/json
```

```json
{
  "userId": 1
}
```

## Update Order Status

```http
PUT /api/orders/1/status?status=SHIPPED
```

---

# Setup and Installation

## 1. Clone the Repository

```bash
git clone <your-repository-url>
cd ShopSphere
```

## 2. Create the Database

Create a MySQL database:

```sql
CREATE DATABASE shopsphere;
```

## 3. Configure the Database

Update `application.properties` with your own database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopsphere
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Do not commit real database credentials or other sensitive information to GitHub.

## 4. Run the Application

Using Maven:

```bash
mvn spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

---

# API Testing

The APIs were tested using Postman.

A typical testing flow is:

```text
1. Create User
2. Create Category
3. Create Product
4. Create Cart
5. Add Product to Cart
6. View Cart
7. Place Order
8. View Order
9. View User's Orders
10. Update Order Status
```

---

# Concepts Practiced

This project was built to practice the following Spring Boot and backend concepts:

* Spring Boot project structure
* REST APIs
* Controllers
* Services
* Repositories
* Dependency Injection
* Spring Data JPA
* Hibernate
* Entity relationships
* `@OneToOne`
* `@OneToMany`
* `@ManyToOne`
* DTOs
* Request validation
* Custom exceptions
* Global exception handling
* Password encryption with BCrypt
* Transactions with `@Transactional`
* Database constraints
* CRUD operations
* Git and GitHub
* API testing with Postman

---

# Future Improvements

Possible improvements for future versions include:

* JWT-based authentication and authorization
* Role-based access control
* Product search and filtering
* Pagination and sorting
* Product images
* Address management
* Payment integration
* Order cancellation rules
* Order status transition validation
* Unit and integration testing
* Dockerization
* API documentation using Swagger/OpenAPI

---

# Project Goal

ShopSphere was created as a learning project to build a complete Spring Boot backend from scratch and understand how different backend components work together.

The primary focus is on understanding Spring Boot fundamentals and implementing a complete e-commerce backend flow rather than implementing every production-level feature.

---

# Author

**Priyansh Shukla**

Spring Boot learning project.
