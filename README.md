# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp now includes in-memory contact management, search/group/count/sort features, file persistence, and database integration with synchronization between memory and database state.

---

## Implemented Use Cases

- `UC1` to `UC16` cover contact management, file persistence, and database retrieval
- `UC17` Update Contact and Sync Memory with Database

---

## UC17 - Update Contact and Sync Memory with Database

### Objective

To update a contact in both memory and the database and verify that both representations remain synchronized.

### Functional Scope

UC17 introduces a coordinated update flow between the in-memory address book and the database layer.  
The feature updates a selected contact in memory, updates the matching database record, and confirms both versions are in sync.

### Implementation Details

- Extended [AddressBookDbService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookDbService.java) with:
  - `getContactFromDb(String addressBookName, String firstName)`
  - `updateContactInDb(String addressBookName, String firstName, Contact updatedContact)`
  - `isMemoryInSyncWithDb(String addressBookName, String firstName, Contact memoryContact)`
- Extended [AddressBookController.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/controller/AddressBookController.java) with:
  - `PUT /api/address-books/{name}/contacts/{firstName}/sync-db`
  - `GET /api/address-books/{name}/db/contacts/{firstName}`
- Updated the DB entity fields for address, city, state, zip, phone number, and email
- Compared the in-memory `Contact` with the database `Contact` to verify synchronization

### Test Coverage

- Added UC17 coverage in [AddressBookControllerTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/controller/AddressBookControllerTest.java)
- Verified that updating a contact updates both memory and database state
- Confirmed that the controller returns the successful sync message
- Confirmed that the retrieved database contact matches the updated in-memory contact

### Outcome

UC17 successfully adds memory-to-database contact synchronization, ensuring that updates stay consistent across both storage layers.

---

## Planned Use Cases

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
