# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp now includes in-memory contact management, file persistence, JPA-based database integration, and database querying features such as retrieval by date range.

---

## Implemented Use Cases

- `UC1` to `UC17` cover contact management, file persistence, database retrieval, and memory/database sync
- `UC18` Retrieve Contacts by Date Range from Database

---

## UC18 - Retrieve Contacts by Date Range from Database

### Objective

To retrieve database contacts whose creation date falls within a specified date range.

### Functional Scope

UC18 introduces date-based querying in the database layer.  
The feature returns only those contacts whose stored `dateAdded` value lies between the requested start and end dates.

### Implementation Details

- Extended [AddressBookDbService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookDbService.java) with:
  - `retrieveContactsAddedBetween(LocalDate fromDate, LocalDate toDate)`
- Converted `LocalDate` values into `LocalDateTime` start/end boundaries
- Queried the repository using a date-range database method
- Returned the result as `List<Contact>` after mapping entities to model objects
- Extended controller support for retrieving contacts by date period

### Test Coverage

- Added UC18 coverage in:
  - [AddressBookDbServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookDbServiceTest.java)
  - [AddressBookControllerTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/controller/AddressBookControllerTest.java)
- Verified that recently stored contacts are returned for the current-date range
- Verified that an old date range with no matching records returns an empty result

### Outcome

UC18 successfully adds date-range retrieval from the database, making it possible to query contacts based on when they were added.

---

## Planned Use Cases

- `UC19` Count Contacts by City or State from Database
- `UC20` Add Contact to Database with Transaction
- `UC21` Add Multiple Contacts to Database Using Threads
- `UC22` Read Entries from JSON Server and Sync Memory
- `UC23` Add Multiple Entries to JSON Server and Sync Memory
- `UC24` Update Entry in JSON Server and Sync Memory
- `UC25` Delete Entry in JSON Server and Sync Memory

---

## Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database**
- **Maven**
- **JUnit 5**
- **Lombok**

---

## Build And Run

```bash
./mvnw clean install
./mvnw test
./mvnw spring-boot:run
```

On Windows:

```bat
mvnw.cmd spring-boot:run
```

---

## Author

**Rudresh Sharma**
