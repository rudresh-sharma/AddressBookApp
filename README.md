# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp now includes in-memory features, file persistence, JPA-based database integration, reporting queries, and transactional database insertion for individual contacts.

---

## Implemented Use Cases

- `UC1` to `UC19` cover contact management, persistence, database retrieval, sync, date-range queries, and grouped database counts
- `UC20` Add Contact to Database with Transaction

---

## UC20 - Add Contact to Database with Transaction

### Objective

To add a contact directly into the database using a transactional workflow while preventing duplicate persistence.

### Functional Scope

UC20 introduces transactional insertion of a single contact into the database.  
The feature ensures the target address book exists, prevents duplicate contact entries in that address book, and persists the new contact across related tables.

### Implementation Details

- Extended [AddressBookDbService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookDbService.java) with:
  - `addContactToAddressBookDb(String addressBookName, Contact contact)`
- Used `@Transactional` to keep the database insert operation consistent
- Checked for an existing contact using address book name, first name, and last name
- Created the address book entity automatically if it did not already exist
- Added the new `ContactEntity` to the `AddressBookEntity` and persisted it
- Returned `false` when a duplicate contact already existed

### Test Coverage

- Added UC20 coverage in:
  - [AddressBookDbServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookDbServiceTest.java)
  - [AddressBookControllerTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/controller/AddressBookControllerTest.java)
- Verified that a new contact is persisted successfully into the database
- Confirmed that the corresponding address book is created when needed
- Verified that duplicate inserts are rejected

### Outcome

UC20 successfully adds transactional database insertion for single contacts, improving persistence reliability and preserving data integrity.

---

## Planned Use Cases

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
