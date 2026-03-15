# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp now includes in-memory features, file persistence, database integration, and JSON Server synchronization for both importing and exporting contact records.

---

## Implemented Use Cases

- `UC1` to `UC22` cover contact management, persistence, database workflows, and JSON Server read/sync
- `UC23` Add Multiple Entries to JSON Server and Sync Memory

---

## UC23 - Add Multiple Entries to JSON Server and Sync Memory

### Objective

To send multiple contacts from the in-memory address book to JSON Server and keep the in-memory state aligned with the synchronized entries.

### Functional Scope

UC23 introduces outbound synchronization to JSON Server.  
The feature reads all contacts from the selected address book, posts them one by one to the remote JSON Server endpoint, and reports how many were added successfully.

### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with:
  - `addContactsToJsonServerAndSyncMemory(String addressBookName, String serverUrl)`
- Used `HttpClient` and POST requests with JSON payloads for each contact
- Serialized contacts using Gson
- Counted successful responses based on HTTP 2xx status codes
- Skipped invalid contacts before sending them to JSON Server
- Added controller support for:
  - `POST /api/address-books/{name}/contacts/json-server/add-multiple`

### Test Coverage

- Added UC23 coverage in [AddressBookRestAssuredTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/controller/AddressBookRestAssuredTest.java)
- Verified that multiple contacts are posted successfully to JSON Server
- Confirmed the success message and added count returned by the API
- Verified that the posted contacts match the expected in-memory entries

### Outcome

UC23 successfully adds outbound synchronization of multiple contacts to JSON Server, enabling the application to publish address book records to an external REST-style store.

---

## Planned Use Cases

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
