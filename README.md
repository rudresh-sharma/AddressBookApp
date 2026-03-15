# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp now includes in-memory contact management, file persistence, JPA-based database integration, and aggregate database queries such as grouped counts by city and state.

---

## Implemented Use Cases

- `UC1` to `UC18` cover contact management, file persistence, database retrieval, sync, and date-range queries
- `UC19` Count Contacts by City or State from Database

---

## UC19 - Count Contacts by City or State from Database

### Objective

To calculate how many contacts belong to each city and state directly from the database.

### Functional Scope

UC19 introduces aggregate database queries for reporting.  
Instead of counting only in memory, the application now retrieves city-wise and state-wise contact counts from persisted database records.

### Implementation Details

- Extended [ContactRepository.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/repository/ContactRepository.java) with custom JPQL queries:
  - `countContactsByCity()`
  - `countContactsByState()`
- Extended [AddressBookDbService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookDbService.java) with:
  - `getPersonCountByCityFromDb()`
  - `getPersonCountByStateFromDb()`
- Converted repository query results from `List<Object[]>` into `Map<String, Long>`
- Preserved result order using `LinkedHashMap`

### Test Coverage

- Added UC19 coverage in [AddressBookDbServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookDbServiceTest.java)
- Verified that city-wise database counts are returned correctly
- Verified that state-wise database counts are returned correctly
- Confirmed expected totals for multiple persisted contacts

### Outcome

UC19 successfully adds database-level grouped counting by city and state, improving reporting support for persisted contact records.

---

## Planned Use Cases

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
