# Banking App API

## Overview
The **Banking App API** is a RESTful service built with **Spring Boot** and **MySQL**, designed to manage user accounts, crediting, and debiting operations securely. This API supports basic banking functionalities such as account creation, deposits, withdrawals, and transaction history tracking.

## Features
- **User Account Management**: Create and manage user bank accounts.
- **Deposit Funds**: Credit user accounts.
- **Withdraw Funds**: Debit user accounts.
- **Transaction History**: View all transactions associated with an account.
- **Security**: Uses JWT authentication for secure access.

## Tech Stack
- **Spring Boot** (Backend Framework)
- **Spring Data JPA** (ORM for database operations)
- **MySQL** (Database)
- **Spring Security & JWT** (Authentication & Authorization)
- **Maven** (Build & Dependency Management)

## Prerequisites
Ensure you have the following installed:
- Java 17+
- Maven
- MySQL Database
- Postman (for API testing, optional)

## Installation & Setup

### 1. Clone the Repository
```sh
 git clone https://github.com/yourusername/banking-app.git
 cd banking-app
```

### 2. Configure the Database
Update **`application.properties`** with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/banking_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build & Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### 1. User Account Management
| Method | Endpoint             | Description       |
|--------|---------------------|------------------|
| POST   | `/api/accounts`      | Create an account |
| GET    | `/api/accounts/{id}` | Get account details |

### 2. Transactions
| Method | Endpoint                   | Description         |
|--------|---------------------------|---------------------|
| POST   | `/api/transactions/credit` | Deposit funds      |
| POST   | `/api/transactions/debit`  | Withdraw funds     |
| GET    | `/api/transactions/{id}`   | Get transaction history |

## Example Requests
### **Create an Account**
```sh
curl -X POST "http://localhost:8080/api/accounts" -H "Content-Type: application/json" -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "initialBalance": 1000
}'
```

### **Deposit Funds**
```sh
curl -X POST "http://localhost:8080/api/transactions/credit" -H "Content-Type: application/json" -d '{
    "accountId": 1,
    "amount": 500
}'
```

### **Withdraw Funds**
```sh
curl -X POST "http://localhost:8080/api/transactions/debit" -H "Content-Type: application/json" -d '{
    "accountId": 1,
    "amount": 200
}'
```

## Security & Authentication
The API uses **JWT-based authentication**. Users must obtain a token via login and include it in the `Authorization` header:
```sh
Authorization: Bearer your_token_here
```

## Future Improvements
- Implement role-based access control (RBAC)
- Introduce multi-factor authentication (MFA)
- Add scheduled interest calculations
- Implement account statements export (PDF/CSV)

## Contributing
Feel free to fork the repo and submit a pull request with your improvements!

## License
This project is licensed under the MIT License.

