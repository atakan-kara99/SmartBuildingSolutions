# Smart Building Solutions – Web & Backend

Smart Building Solutions is a software system designed to support construction companies in managing contracts, projects, and performance items digitally. The platform provides a **web interface** for administrators and project stakeholders, as well as a **backend service** that synchronizes project data and provides secure data access.

> **Note:** This repository contains the **web application** and **backend services only**.
> The [Android mobile application](https://github.com/atakan-kara99/SmartBuildingSolutions-AndroidApp) is **not included**.

---

## Features

### ✅ Core Functionality

* User authentication and role-based access control
* Management of:

  * Organizations
  * Users & roles
  * Projects and contracts
  * Billing units & performance items (Leistungspositionen)
* REST-based data synchronization with external project data provider
* Dashboard with real-time project status information
* Interactive chart & diagram views

### 👥 User Roles

* **System Administrator (SysAdmin):**

  * Manages organizations and assigns OrgAdmins
* **Organization Administrator (OrgAdmin):**

  * Manages users and roles inside organization
* **Web User:**

  * Views project & contract data and updates performance item statuses

---

## Technology Stack

### Backend

* **Java 11**
* **Spring Boot**

  * Spring Data JPA
  * Spring Security
  * Validation
* **H2 Database** (local development)
* **Gradle** build system
* **REST API** integration for external data source

### Web Frontend

* **Spring MVC with Thymeleaf**
* **Bootstrap 5**
* **Chart.js** for diagrams & stats

---

## Architecture

### Server Architecture

* Backend service provides:

  * Data access layer
  * Role-based security
  * External REST data synchronization service
* Web layer handles user interaction (controllers + templates)

### Components Overview

| Layer        | Responsibilities                        |
| ------------ | --------------------------------------- |
| Controllers  | HTTP endpoints, routing, validation     |
| Services     | Business logic, permission checks       |
| Repositories | Database access (Spring Data JPA)       |
| Sync Service | Pulls and updates external project data |
| Views        | Thymeleaf UI templates                  |

---

## Data Model Overview

Main entities include:

* User
* Role
* Organization
* Project
* Contract
* BillingUnit
* BillingItem
