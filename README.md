# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp now includes in-memory features, file persistence, JPA-based database integration, transactional writes, and parallel insertion of multiple contacts into the database.

---

## Implemented Use Cases

- `UC1` to `UC20` cover contact management, persistence, database querying, and transactional single-contact insertion
- `UC21` Add Multiple Contacts to Database Using Threads

---

## UC21 - Add Multiple Contacts to Database Using Threads

### Objective

To insert multiple contacts into the database concurrently while preserving transactional consistency and avoiding duplicate address book creation races.

### Functional Scope

UC21 introduces multi-threaded database insertion.  
The feature accepts a list of contacts, uses a thread pool to insert them in parallel, and returns the total number of successfully added entries.

### Implementation Details

- Extended [AddressBookDbService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookDbService.java) with:
  - `addContactsToAddressBookDbUsingThreads(String addressBookName, List<Contact> contacts)`
- Used `ExecutorService` with a fixed thread pool
- Used `CompletableFuture` for concurrent execution
- Wrapped each insert in `TransactionTemplate` so every thread runs in its own transaction
- Ensured the target address book exists before parallel insertion starts to avoid duplicate creation races
- Counted successful inserts by joining all futures and summing their results

### Test Coverage

- Added UC21 coverage in:
  - [AddressBookDbServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookDbServiceTest.java)
  - [AddressBookControllerTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/controller/AddressBookControllerTest.java)
- Verified that multiple contacts are inserted successfully through threaded execution
- Confirmed that the address book is created and all contacts are persisted
- Verified that the controller returns the expected success message and count

### Outcome

UC21 successfully adds multi-threaded database insertion, improving throughput for bulk contact persistence while maintaining transactional safety.

---

## Planned Use Cases

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
