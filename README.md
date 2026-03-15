# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp now includes in-memory features, file persistence, database integration, and full JSON Server synchronization flows for reading, adding, updating, and deleting contact records.

---

## Implemented Use Cases

- `UC1` to `UC24` cover contact management, persistence, database workflows, and JSON Server read/add/update synchronization
- `UC25` Delete Entry in JSON Server and Sync Memory

---

## UC25 - Delete Entry in JSON Server and Sync Memory

### Objective

To delete a contact entry from JSON Server and remove the same contact from in-memory storage in a synchronized flow.

### Functional Scope

UC25 introduces remote delete synchronization.  
The feature finds the target contact in JSON Server, deletes the remote record, and then removes the matching contact from the in-memory address book when the remote delete succeeds.

### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with:
  - `deleteContactInJsonServerAndSyncMemory(String addressBookName, String firstName, String serverUrl)`
- Verified the contact exists in memory before attempting deletion
- Queried JSON Server using `firstName` to locate the matching remote entry
- Extracted the remote `id` from the JSON response
- Sent an HTTP DELETE request to `/contacts/{id}`
- Removed the contact from memory only after the remote delete returned a success status
- Added controller support for:
  - `DELETE /api/address-books/{name}/contacts/{firstName}/json-server/sync`

### Test Coverage

- Added UC25 coverage in [AddressBookRestAssuredTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/controller/AddressBookRestAssuredTest.java)
- Verified that the contact is deleted from JSON Server
- Verified that the contact is removed from the in-memory address book
- Confirmed the success response message from the API
- Confirmed that the deleted contact no longer exists in the remote JSON Server dataset

### Outcome

UC25 successfully adds delete-and-sync support between JSON Server and memory, completing the full remote synchronization cycle for contact records.

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
