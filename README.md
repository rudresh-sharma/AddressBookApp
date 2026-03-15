# AddressBookApp

> A Spring Boot based Java application developed using Test-Driven Development (TDD) to progressively build a digital Address Book system. The project focuses on incremental development, clean structure, and step-by-step implementation of contact management features.

### Overview

- Modular Spring Boot project for creating and managing Address Book contacts.
- Built around incremental Use Cases, starting from the core contact model and expanding feature by feature.
- Follows a layered structure with clear separation between model, service, and controller components.
- Emphasizes testability, maintainability, and gradual project evolution through TDD.

---

### Implemented Features

> Features will be updated as new Use Cases are completed.

- **UC1 - Create Contact**
  - Introduces the core `Contact` model for representing an address book entry.
  - Defines the main contact fields: first name, last name, address, city, state, zip, phone number, and email.
  - Establishes the base domain object used by the rest of the application.

  **Purpose**
  - To define a structured representation of a contact in the Address Book system.
  - To provide the foundational object needed for future operations like add, edit, delete, and search.

  **Implementation**
  - Implemented `Contact` in the model layer at `src/main/java/com/addressbookapp/model/Contact.java`.
  - Used Lombok annotations to reduce boilerplate for constructors, getters, setters, and utility methods.
  - Added a unit test for contact creation in `src/test/java/com/addressbookapp/service/AddressBookServiceTest.java`.
  - Verified that contact objects store and return values correctly.

---

- **UC2 - Add Contact to Address Book**  
  _Pending implementation._

- **UC3 - Edit Existing Contact**  
  _Pending implementation._

- **UC4 - Delete Contact**  
  _Pending implementation._

- **UC5 - Prevent Duplicate Entries**  
  _Pending implementation._

- **UC6 - Multiple Address Books**  
  _Pending implementation._

- **UC7 - Search Person by City or State**  
  _Pending implementation._

- **UC8 - View Persons by City or State**  
  _Pending implementation._

- **UC9 - Count Contacts by City or State**  
  _Pending implementation._

- **UC10 - Sort Contacts Alphabetically**  
  _Pending implementation._

- **UC11 - Sort Contacts by City, State, or Zip**  
  _Pending implementation._

- **UC12 - Write Address Book to File**  
  _Pending implementation._

- **UC13 - Read Address Book from File**  
  _Pending implementation._

- **UC14 - Count Contacts in File**  
  _Pending implementation._

- **UC15 - Write Contacts to CSV File**  
  _Pending implementation._

- **UC16 - Read Contacts from CSV File**  
  _Pending implementation._

- **UC17 - Write Contacts to JSON File**  
  _Pending implementation._

- **UC18 - Read Contacts from JSON File**  
  _Pending implementation._

- **UC19 - Add Contacts Using Threads**  
  _Pending implementation._

- **UC20 - Measure Time for Threaded Contact Addition**  
  _Pending implementation._

- **UC21 - Add Multiple Contacts Using Thread Pools**  
  _Pending implementation._

- **UC22 - Measure Thread Pool Performance**  
  _Pending implementation._

- **UC23 - Store Address Book in Database**  
  _Pending implementation._

- **UC24 - Retrieve Contacts from Database**  
  _Pending implementation._

- **UC25 - Update Contact in Database**  
  _Pending implementation._

---

### Tech Stack

- **Java 17+** - core programming language
- **Spring Boot** - application framework
- **Maven** - build and dependency management
- **JUnit 5** - testing framework for TDD
- **Lombok** - reduces boilerplate in model classes

---

### Build / Run

Build the project:

```bash
./mvnw clean install
```

Run tests:

```bash
./mvnw test
```

Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

On Windows Command Prompt:

```bat
mvnw.cmd spring-boot:run
```

---

### Project Structure

```text
AddressBookApp
|
|-- .mvn
|-- src
|   |-- main
|   |   |-- java
|   |   |   |-- com
|   |   |   |   |-- addressbookapp
|   |   |   |   |   |-- controller
|   |   |   |   |   |   -- AddressBookController.java
|   |   |   |   |   |-- model
|   |   |   |   |   |   -- Contact.java
|   |   |   |   |   |-- service
|   |   |   |   |   |   -- AddressBookService.java
|   |   |   |   |   -- AddressBookApplication.java
|   |   -- resources
|   |       -- application.properties
|   -- test
|       -- java
|           -- com
|               -- addressbookapp
|                   |-- service
|                   |   -- AddressBookServiceTest.java
|                   |-- controller
|                   |   -- AddressBookControllerTest.java
|                   -- AddressBookApplicationTests.java
|-- pom.xml
|-- mvnw
|-- mvnw.cmd
-- README.md
```

---

### Development Approach

> This project follows an incremental TDD workflow:

- Tests are written first to define expected behavior.
- Code is implemented to satisfy the tests.
- Each Use Case introduces one controlled enhancement at a time.
- Refactoring is done carefully to preserve existing behavior.
- The project grows progressively from core models to service and controller layers.

---

### Author

**Abhishek Puri Goswami**  
Java developer focused on clean architecture, object-oriented design, and Test-Driven Development.

---

<div align="center">
Built incrementally using Test-Driven Development.
</div>
