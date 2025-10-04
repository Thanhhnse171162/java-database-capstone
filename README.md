# Retail Management Backend System

This repository contains the full backend implementation of the Retail Management System using Java, Spring Boot, and Hibernate.

## Overview

The Retail Management Backend System is a comprehensive solution for managing retail operations including:

- Product management
- Inventory tracking
- Customer management
- Order processing
- Sales analytics
- Staff management

## Features

- **Product Management**: Add, update, delete, and search products
- **Inventory Control**: Track stock levels and manage inventory
- **Customer Management**: Maintain customer records and purchase history
- **Order Processing**: Handle orders from creation to fulfillment
- **Sales Analytics**: Generate reports and analytics
- **Staff Management**: Manage employee information and roles

## Technology Stack

- **Java 8+**
- **Spring Boot 2.7.18**
- **Spring Data JPA**
- **Hibernate**
- **MySQL Database**
- **Maven**
- **RESTful APIs**

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6+
- MySQL 8.0+

### Installation

1. Clone the repository:
```bash
git clone https://github.com/Thanhhnse171162/retail-management-backend.git
cd retail-management-backend
```

2. Configure database:
```bash
# Update application.properties with your MySQL credentials
```

3. Run the application:
```bash
mvn spring-boot:run
```

## API Endpoints

### Products
- `GET /api/products` - Get all products
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product

### Customers
- `GET /api/customers` - Get all customers
- `POST /api/customers` - Create new customer
- `PUT /api/customers/{id}` - Update customer
- `DELETE /api/customers/{id}` - Delete customer

### Orders
- `GET /api/orders` - Get all orders
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}` - Update order
- `GET /api/orders/{id}` - Get order by ID

## Database Schema

The system uses the following main entities:
- Products
- Customers
- Orders
- OrderItems
- Staff
- Categories

## Contributing

Please read our [CONTRIBUTING.md](CONTRIBUTING.md) file for details on our code of conduct and the process for submitting pull requests.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Contact

For questions or support, please contact the development team.Testing merge 
