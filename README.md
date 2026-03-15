# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp now includes in-memory features, file persistence, database integration, and JSON Server synchronization for reading, exporting, and updating contact records.

---

## Implemented Use Cases

- `UC1` to `UC23` cover contact management, persistence, database workflows, and JSON Server read/export sync
- `UC24` Update Entry in JSON Server and Sync Memory

---

## UC24 - Update Entry in JSON Server and Sync Memory

### Objective

To update an existing contact entry in JSON Server using the latest in-memory contact data.

### Functional Scope

UC24 introduces remote update synchronization.  
The feature locates the matching contact in JSON Server, resolves its remote identifier, and updates the external record using the current in-memory contact values.

### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with:
  - `updateContactInJsonServerAndSyncMemory(String addressBookName, String firstName, String serverUrl)`
- Queried JSON Server using `firstName` as the lookup parameter
- Parsed the returned JSON array to extract the matching record `id`
- Sent a PUT request to `/contacts/{id}` with the current memory contact serialized as JSON
- Added controller support for:
  - `PUT /api/address-books/{name}/contacts/{firstName}/json-server/sync`

### Test Coverage

- Added UC24 coverage in [AddressBookRestAssuredTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/controller/AddressBookRestAssuredTest.java)
- Verified that updating a contact in memory results in an updated record in JSON Server
- Confirmed the success response message from the API
- Verified that the updated city, state, and email are reflected in the remote JSON Server data

### Outcome

UC24 successfully adds JSON Server update synchronization, keeping remote contact data aligned with the latest in-memory state.

---

## Planned Use Cases

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
