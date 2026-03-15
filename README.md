# AddressBookApp

> A Spring Boot based Java application built incrementally using Test-Driven Development (TDD). The project is organized around individual use cases, with each use case introducing one clear enhancement to the Address Book domain while keeping the codebase structured, testable, and easy to extend.

## Overview

AddressBookApp is a progressive contact management project that begins with the core domain model and expands through a sequence of focused use cases.  
Each stage adds a small, testable improvement so the application can grow in a controlled and maintainable way.

At its current stage, the project establishes the foundation of the system through the `Contact` model, which will later support operations such as adding contacts, editing details, deleting entries, searching records, sorting data, and integrating persistence mechanisms.

---

## Use Case Documentation Approach

This repository is intended to be documented use case by use case.  
For every new UC, the README can be extended using the same professional structure:

- Objective
- Functional scope
- Implementation details
- Test coverage
- Outcome

This keeps the project history clean and makes progress easy to present during reviews, submissions, and interviews.

---

## Implemented Use Cases

### UC1 - Create Contact

#### Objective

To define the fundamental domain object of the Address Book system by creating a `Contact` model that stores all primary contact information in a structured form.

#### Functional Scope

UC1 establishes the base entity required for the application.  
It introduces a contact as a single record containing the key personal and communication details needed by the address book.

#### Contact Attributes

The `Contact` model includes:

- First name
- Last name
- Address
- City
- State
- Zip
- Phone number
- Email

#### Implementation Details

- Implemented the `Contact` domain model in [src/main/java/com/addressbookapp/model/Contact.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/model/Contact.java)
- Used Lombok annotations to generate constructors, getters, setters, and common utility methods
- Kept the model simple and focused so it can serve as the base for all future address book operations

#### Test Coverage

- Added UC1 validation in [src/test/java/com/addressbookapp/service/AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that a contact object can be created successfully
- Confirmed that the stored values can be accessed correctly through the model

#### Outcome

UC1 successfully establishes the core contact representation for the application and provides the foundation for upcoming use cases such as adding contacts to an address book and editing existing entries.

---

## Planned Use Cases

- `UC2` - Add Contact to Address Book
- `UC3` - Edit Existing Contact
- `UC4` - Delete Contact
- `UC5` - Prevent Duplicate Entries
- `UC6` - Multiple Address Books
- `UC7` - Search Person by City or State
- `UC8` - View Persons by City or State
- `UC9` - Count Contacts by City or State
- `UC10` - Sort Contacts Alphabetically
- `UC11` - Sort Contacts by City, State, or Zip
- `UC12` - Write Address Book to File
- `UC13` - Read Address Book from File
- `UC14` - Count Contacts in File
- `UC15` - Write Contacts to CSV File
- `UC16` - Read Contacts from CSV File
- `UC17` - Write Contacts to JSON File
- `UC18` - Read Contacts from JSON File
- `UC19` - Add Contacts Using Threads
- `UC20` - Measure Time for Threaded Contact Addition
- `UC21` - Add Multiple Contacts Using Thread Pools
- `UC22` - Measure Thread Pool Performance
- `UC23` - Store Address Book in Database
- `UC24` - Retrieve Contacts from Database
- `UC25` - Update Contact in Database

---

## Tech Stack

- **Java 17** for core application development
- **Spring Boot** for application structure and service configuration
- **Maven** for dependency management and build automation
- **JUnit 5** for test-first development
- **Lombok** for reducing model boilerplate

---

## Build And Run

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

On Windows:

```bat
mvnw.cmd spring-boot:run
```

---

## Project Structure

```text
AddressBookApp
|
|-- src
|   |-- main
|   |   |-- java
|   |   |   -- com
|   |   |      -- addressbookapp
|   |   |         |-- controller
|   |   |         |-- model
|   |   |         |-- service
|   |   |         -- AddressBookApplication.java
|   |   -- resources
|   |      -- application.properties
|   -- test
|      -- java
|         -- com
|            -- addressbookapp
|               |-- controller
|               |-- service
|               -- AddressBookApplicationTests.java
|-- pom.xml
|-- mvnw
|-- mvnw.cmd
-- README.md
```

---

## Development Approach

The project follows a disciplined TDD workflow:

- Write a test for the next required behavior
- Implement the minimum code necessary to satisfy the test
- Refactor while preserving correctness
- Document progress use case by use case

This approach helps ensure clarity, correctness, and steady project growth.

---

## Author

**Rudresh Sharma**

---

<div align="center">
Incrementally developed with Test-Driven Development and use-case-based implementation.
</div>
