# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp now includes in-memory features, file persistence, database integration, and external JSON Server synchronization for importing contact records.

---

## Implemented Use Cases

- `UC1` to `UC21` cover contact management, persistence, database querying, and threaded DB insertion
- `UC22` Read Entries from JSON Server and Sync Memory

---

## UC22 - Read Entries from JSON Server and Sync Memory

### Objective

To fetch contact entries from a JSON Server endpoint and synchronize them into the in-memory address book.

### Functional Scope

UC22 introduces remote JSON import over HTTP.  
The feature reads contact data from a JSON Server endpoint, validates the received entries, and adds them into the selected in-memory address book.

### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with:
  - `readContactsFromJsonServer(String addressBookName, String serverUrl)`
- Used `HttpClient`, `HttpRequest`, and `HttpResponse` to fetch remote JSON data
- Parsed the JSON payload into `List<Contact>` using Gson
- Validated incoming contacts before adding them to memory
- Returned the count of successfully added contacts
- Added Rest Assured coverage against a mock HTTP server

### Test Coverage

- Added UC22 coverage in [AddressBookRestAssuredTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/controller/AddressBookRestAssuredTest.java)
- Verified that remote contacts are read successfully from JSON Server
- Confirmed that imported entries are synchronized into the target address book
- Verified success response message and imported count

### Outcome

UC22 successfully adds JSON Server read-and-sync support, allowing the application to import external contact data into memory.

---

## Planned Use Cases

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
- **Gson**
- **Rest Assured**

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
