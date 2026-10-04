# Inventory Management System — Layered Architecture

A JavaFX desktop application for inventory management built with a clean **Controller → BO → DAO** layered architecture, MySQL persistence, and JasperReports for reporting.

Built for the **ITS 1118 – Application Programming** module coursework at IJSE.

## Features

- **Login system** with session management
- **Dashboard** with overview stats
- **Customer management** — add, update, view, search
- **Item management** — add, update, view items with categories
- **Supplier management** — manage suppliers and their items
- **Order management** — create and track orders
- **Quick Sale** — fast checkout flow
- **Warranty tracking** — manage warranty records
- **User management** — manage system users
- **Reports** — generate order and bill reports via JasperReports

## Tech Stack

- **Java** + **JavaFX** (FXML UI with CSS styling)
- **Maven** for dependency management
- **MySQL** for data persistence
- **JasperReports** for PDF/report generation
- **Layered Architecture** (Controller → BO → DAO pattern)

## Architecture

The application follows a strict layered architecture:

```
┌─────────────────────────────────────┐
│           Controllers (UI)          │
│    Handles JavaFX FXML events       │
├─────────────────────────────────────┤
│         Business Objects (BO)       │
│    Business logic & validation      │
│    BOFactory → *BOImpl              │
├─────────────────────────────────────┤
│       Data Access Objects (DAO)     │
│    Database CRUD operations         │
│    DAOFactory → *DAOImpl            │
├─────────────────────────────────────┤
│              MySQL                  │
└─────────────────────────────────────┘
```

- **Controller layer** — JavaFX controllers for each FXML view
- **BO layer** — Business Objects with interface + implementation pattern (`BOFactory` creates instances)
- **DAO layer** — Data Access Objects with `CrudDAO` / `SuperDAO` interfaces, `DAOFactory` for instantiation
- **DTO layer** — Data Transfer Objects for passing data between layers
- **Entity layer** — JPA entities mapping to database tables

## Project Structure

```
src/main/java/lk/ijse/inventorymanagmentsystem/
├── App.java                 # Main application entry point
├── controller/              # JavaFX FXML controllers
├── bo/                      # Business Object layer
│   ├── BOFactory.java
│   ├── SuperBO.java
│   ├── custom/              # BO interfaces
│   └── custom/impl/         # BO implementations
├── dao/                     # Data Access Object layer
│   ├── DAOFactory.java
│   ├── SuperDAO.java
│   ├── CrudDAO.java
│   ├── custom/              # DAO interfaces
│   └── custom/impl/         # DAO implementations
├── dto/                     # Data Transfer Objects
├── entity/                  # JPA entity classes
├── db/                      # Database connection
└── util/                    # Utilities (Navigation, Reports, Session)
```

## Getting Started

### Prerequisites

- JDK 17+
- Maven
- MySQL server

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/kalpanath-selvaraja/ITS-1118--Layered-Architecture-Project.git
   cd ITS-1118--Layered-Architecture-Project
   ```

2. Configure your MySQL connection in `DBConnection.java`

3. Build and run with Maven:
   ```bash
   mvn clean javafx:run
   ```

## Entities

`Customer`, `Employee`, `Item`, `Order`, `OrderItem`, `Supplier`, `SupplierItem`, `User`, `Warranty`
