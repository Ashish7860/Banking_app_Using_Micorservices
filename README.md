# Banking_app_Using_Micorservices
# Banking Application MVP with Microservices Architecture

## Project Overview
This project demonstrates a banking application built using **Microservices Architecture**. It includes two core services, **Customer Management Service** and **Account Management Service**, along with supporting components like **API Gateway**, **Eureka Server**, and **Centralized Configuration Management**. 

The system ensures modularity, scalability, and ease of maintenance, adhering to best practices for microservices.

---

## Microservices and APIs

### 1. **Customer Management Service**
Manages customer-related operations:
- **Add Customer**: Add a new customer to the system.
- **Get All Customers**: Retrieve a list of all customers.
- **Get Single Customer Details**: Retrieve details of a specific customer.
- **Update Customer Details**: Modify customer information.
- **Delete Customer**: Delete a customer and automatically remove the corresponding account from the **Account Management Service**.

### 2. **Account Management Service**
Handles account-related operations:
- **Add Money**: Add money to an account. Ensures the customer details provided in the request are valid.
- **Withdraw Money**: Withdraw money from an account. Validates customer details before proceeding.
- **Get Account Details**: Fetch account details along with related customer information.
- **Delete Account**: Remove an account from the system.

---

## Supporting Components

### 1. **API Gateway**
- Acts as the single entry point for all client requests.
- Routes requests to the respective microservices.

### 2. **Eureka Server**
- Used for service registration and discovery.
- Ensures all microservices can locate and communicate with each other dynamically.

### 3. **Centralized Configuration Management**
- Provides a centralized repository for configuration properties of all microservices.
- Simplifies the management of environment-specific configurations.

---

## Cross-Cutting Concerns
### 1. **Exception Handling**
- Ensures all services handle exceptions gracefully.
- Provides meaningful error messages to clients.

### 2. **Centralized Configuration Management**
- All configuration details are managed through a centralized service, enabling consistent and efficient management.

---

## Tools and Technologies
- **Backend**: Java 8, Spring Boot
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Configuration Management**: Spring Cloud Config Server
- **Database**: MySQL
- **Testing**: Postman
- **Build Tool**: Maven
- **Version Control**: Git

---

## Steps to Set Up and Run the Project

### Prerequisites
1. Java 8 or higher
2. Maven
3. MySQL
4. Postman
5. Git

### Setup Instructions

#### 1. Clone the Repository
```bash
git clone <repository-url>
cd banking-application-mvp
```

#### 2. Start MySQL
- Create two databases:
  - `customer_db`
  - `account_db`

#### 3. Configure Spring Cloud Config Server
- Navigate to the `config-server` directory.
- Set up a local Git repository or use an online Git repository to store configuration files.
- Start the Config Server:
  ```bash
  mvn spring-boot:run
  ```

#### 4. Start the Eureka Server
- Navigate to the `eureka-server` directory.
- Run the server:
  ```bash
  mvn spring-boot:run
  ```

#### 5. Start Microservices
- **Customer Management Service**:
  ```bash
  cd customer-management-service
  mvn spring-boot:run
  ```
- **Account Management Service**:
  ```bash
  cd account-management-service
  mvn spring-boot:run
  ```

#### 6. Start the API Gateway
- Navigate to the `api-gateway` directory.
- Run the gateway:
  ```bash
  mvn spring-boot:run
  ```

#### 7. Test APIs Using Postman
- Import the provided Postman collection to test all endpoints.
- Verify requests and responses for both **Customer Management** and **Account Management** services.

---

## Postman API Endpoints

### Customer Management Service
| Method | Endpoint                  | Description                |
|--------|---------------------------|----------------------------|
| POST   | `/customers`              | Add a new customer         |
| GET    | `/customers`              | Retrieve all customers     |
| GET    | `/customers/{id}`         | Retrieve a specific customer |
| PUT    | `/customers/{id}`         | Update customer details    |
| DELETE | `/customers/{id}`         | Delete a customer          |

### Account Management Service
| Method | Endpoint                  | Description                |
|--------|---------------------------|----------------------------|
| POST   | `/accounts`               | Add money to an account    |
| PATCH  | `/accounts/withdraw`      | Withdraw money             |
| GET    | `/accounts/{id}`          | Retrieve account details   |
| DELETE | `/accounts/{id}`          | Delete an account          |

---
