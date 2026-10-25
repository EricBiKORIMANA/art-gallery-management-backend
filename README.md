# Art Gallery Management Backend

## Author Information

**Name:** Eric BIKORIMANA

**Student ID:** 27928

**Course:** Web Technology and Internet (INSY 8322)

**Institution:** Adventist University of Central Africa (AUCA)

---

## Project Overview

The **Art Gallery Management** backend system organizes how a gallery exhibits the work of local artists. It handles artist proposals, curator assignments, exhibition hall management, specialized art styles, dated bookings with status flows (PROPOSED, ACCEPTED, EXHIBITED, DECLINED), and catalogued displayed artworks once an exhibition takes place.

Built using **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**.

---

## Tech Stack & Prerequisites

* **Java Development Kit (JDK):** 17 or higher
* **Build Tool:** Maven
* **Database:** PostgreSQL
* **API Testing Tool:** Postman

---

## Database Configuration

1. Open your PostgreSQL client and create a database named `art_gallery_management`:

## Database Configuration

1. Open your PostgreSQL client and create a database named `art_gallery_management`:

   ```sql
   CREATE DATABASE art_gallery_management;
   ```
2. Update your `src/main/resources/application.properties` file with your local database credentials:

   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/art_gallery_management
   spring.datasource.username=postgres
   spring.datasource.password=your_database_password

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.serve=8000
   ```

## Steps to Run the API

1. **Clone the Repository:**

   ```
   git clone https://github.com/EricBiKORIMANA/art-gallery-management-backend.git
   cd art-gallery-management-backend
   ```
2. **Build the Project:**

   ```
   mvn clean install
   ```
3. **Run the Spring Boot Application:**

   ```
   mvn spring-boot:run
   ```

   *Alternatively, you can run the main application class `ArtGalleryApplication.java` directly from your IDE (Vs Code, IntelliJ IDEA, Eclipse).*
4. **Access the API:**
   The application will start on port `8000`. You can send HTTP requests to endpoints starting with `/api/...` (e.g., `http://localhost:8000/api/artists/all`).

## Postman Collection

The complete Postman test collection containing requests for all core requirements, derived queries, JPQL joins, business rules, reports, and bonus features is included in the root directory:

* `art-gallery-management-midterm.postman_collection.json`

Here is the comprehensive table containing all the API endpoints required across all parts of the **Art Gallery Management** project:

| **Part / ID**  | **HTTP Method** | **Endpoint**                                             | **Description**                                                                                    |
| -------------------- | --------------------- | -------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- |
| **A2 (CRUD)**  | `POST`              | `/api/{path}/save`                                           | Save a new entity (`art-styles`,`exhibition-halls`,`curators`,`artists`,`exhibition-bookings`) |
| **A2 (CRUD)**  | `GET`               | `/api/{path}/all`                                            | Retrieve all records for a given entity type                                                             |
| **A2 (CRUD)**  | `GET`               | `/api/{path}/{id}`                                           | Retrieve a specific record by its UUID                                                                   |
| **A2 (CRUD)**  | `PUT`               | `/api/{path}/update/{id}`                                    | Update an existing record by its UUID                                                                    |
| **A2 (CRUD)**  | `DELETE`            | `/api/{path}/delete/{id}`                                    | Delete a record by its UUID                                                                              |
| **B1**         | `GET`               | `/api/artists/by-last-name?lastName=...`                     | Find artists by last name (case-insensitive, sorted A-Z)                                                 |
| **B2**         | `GET`               | `/api/exhibition-bookings/by-status?status=...`              | Find exhibition bookings by status, earliest date first                                                  |
| **B3**         | `GET`               | `/api/exhibition-bookings/between?start=...&end=...`         | Find exhibition bookings within a specific date range                                                    |
| **C1**         | `GET`               | `/api/curators/by-art-style?name=...`                        | Find curators holding a specific art style (case-insensitive)                                            |
| **C2**         | `GET`               | `/api/exhibition-halls/free`                                 | Find exhibition halls not occupied by any curator                                                        |
| **C3**         | `GET`               | `/api/art-styles/unused`                                     | Find art styles that no curator holds                                                                    |
| **C4**         | `GET`               | `/api/artists/of-curator/{curatorId}`                        | Find distinct artists associated with a specific curator                                                 |
| **D1**         | `POST`              | `/api/exhibition-bookings/save`                              | Safe save with date validations and conflict checks (starts as`PROPOSED`)                              |
| **D2**         | `PATCH`             | `/api/exhibition-bookings/{id}/status?status=...`            | Progress booking status following strict state-machine rules                                             |
| **D3**         | `PUT`               | `/api/curators/{curatorId}/art-styles/{artStyleId}`          | Add an art style to a curator's list without removing existing ones                                      |
| **D4**         | `PATCH`             | `/api/curators/{curatorId}/exhibition-hall?hallNumber=...`   | Reassign a curator to a new exhibition hall and free up the old one                                      |
| **E1**         | `POST`              | `/api/displayed-artworks/save?exhibitionBookingId=...`       | Record a displayed artwork for an`EXHIBITED`booking                                                    |
| **E2**         | `GET`               | `/api/displayed-artworks/artist/{artistCode}`                | Retrieve the displayed artwork history of an artist (newest first)                                       |
| **E3**         | `DELETE`            | `/api/displayed-artworks/delete/{id}`                        | Delete a displayed artwork record                                                                        |
| **E3**         | `DELETE`            | `/api/exhibition-bookings/delete/{id}`                       | Delete a booking (protected if it contains displayed artworks)                                           |
| **F1**         | `GET`               | `/api/exhibition-bookings/stats/by-status`                   | Get exhibition booking statistics grouped by status with counts                                          |
| **F2**         | `PATCH`             | `/api/exhibition-bookings/cancel-day?curatorId=...&date=...` | Bulk cancel a curator's schedule on a given day (sets to`DECLINED`)                                    |
| **Bonus** | `GET`               | `/api/dashboard/summary`                                     | Retrieve a dashboard JSON object with total counts and metrics                                           |
| **Bonus** | `GET`               | `/api/exhibition-bookings/page?page=...&size=...&sort=...`   | Retrieve a paginated and sorted list of exhibition bookings                                              |
