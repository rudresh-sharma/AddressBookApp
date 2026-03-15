# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp is built one use case at a time, starting from the core `Contact` model and expanding into contact management, duplicate prevention, multiple address books, search, grouping, counting, sorting, file persistence, and database integration.

At this stage, the project supports saving address books into a database and retrieving all stored contacts using Spring Data JPA.

---

## Implemented Use Cases

- `UC1` to `UC15` cover in-memory operations, search, grouping, counting, sorting, text/CSV/JSON persistence
- `UC16` Database Retrieval with Spring Data JPA

---

## UC16 - Retrieve Contacts from Database

### Objective

To store contacts in a relational database and retrieve all saved contact entries back into the application.

### Functional Scope

UC16 introduces database-backed persistence and retrieval.  
Address books with contacts can be saved as JPA entities, and all contact records can be read back from the database as model objects.

### Implementation Details

- Added [AddressBookDbService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookDbService.java)
- Added JPA entity classes:
  - [AddressBookEntity.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/entity/AddressBookEntity.java)
  - [ContactEntity.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/entity/ContactEntity.java)
- Added Spring Data repositories:
  - [AddressBookRepository.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/repository/AddressBookRepository.java)
  - [ContactRepository.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/repository/ContactRepository.java)
- Implemented:
  - `saveAddressBookWithContacts(String name, List<Contact> contacts)`
  - `retrieveAllEntriesFromDb()`
- Mapped database entities back to the `Contact` model using `toModel(...)`
- Used `@Transactional` to manage database operations safely

### Test Coverage

- Added UC16 coverage in [AddressBookDbServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookDbServiceTest.java)
- Verified that contacts saved in the database can be retrieved successfully
- Confirmed that retrieved entries match the originally stored contacts

### Outcome

UC16 successfully introduces Spring Data JPA persistence and retrieval, allowing contact records to move beyond in-memory storage into the database layer.

---

## Planned Use Cases

- `UC17` Update Contact and Sync Memory with Database
- `UC18` Retrieve Contacts by Date Range from Database
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
- **OpenCSV**
- **Gson**

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
