# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp is built one use case at a time, starting from the core `Contact` model and expanding into contact management, duplicate prevention, multiple address books, search, grouping, counting, sorting, and file persistence.

At this stage, the project supports JSON-based persistence using Gson.

---

## Implemented Use Cases

- `UC1` Create Contact
- `UC2` Add Contact to Address Book
- `UC3` Edit Existing Contact
- `UC4` Delete Contact
- `UC5` Add Multiple Contacts
- `UC6` Multiple Address Books
- `UC7` Prevent Duplicate Contacts
- `UC8` Search Person by City or State
- `UC9` View Persons by City or State
- `UC10` Count Contacts by City or State
- `UC11` Sort Contacts Alphabetically
- `UC12` Sort Contacts by City, State, or Zip
- `UC13` File I/O Read and Write
- `UC14` CSV Read and Write with OpenCSV
- `UC15` JSON Read and Write with Gson

---

## UC15 - JSON File I/O

### Objective

To persist address book contacts in JSON format and restore them back into an address book.

### Functional Scope

UC15 introduces JSON-based export and import.  
Contacts from an address book can be written to a JSON file and later read back into another address book while preserving full contact structure.

### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with:
  - `writeContactsToJsonFile(String addressBookName, String filePath)`
  - `readContactsFromJsonFile(String addressBookName, String filePath)`
- Used Gson with pretty printing for JSON serialization
- Serialized the address book contact list directly into JSON
- Deserialized JSON into `List<Contact>` using `TypeToken`
- Returned `false` or `-1` for invalid address books or read/write failures
- Ignored `null` contact entries during import

### Test Coverage

- Added UC15 coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that contacts can be written to JSON and read back into another address book
- Confirmed that imported contact count matches the original records
- Verified that field values such as address remain intact after round-trip import

### Outcome

UC15 successfully adds JSON-based persistence using Gson, enabling structured and readable export/import of address book data.

---

## Planned Use Cases

- `UC16` Retrieve Contacts from Database
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
