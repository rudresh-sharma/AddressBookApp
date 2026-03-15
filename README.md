# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp is built one use case at a time, starting from the core `Contact` model and expanding into contact management, duplicate prevention, multiple address books, search, grouping, counting, sorting, and file persistence.

At this stage, the project supports writing an address book to a text file and reading it back into another address book.

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
- `UC13` Write and Read Address Book Using File I/O

---

## UC13 - File I/O Read and Write

### Objective

To persist address book contacts into a text file and restore them back into an address book when needed.

### Functional Scope

UC13 introduces basic file persistence.  
Contacts from an address book can be serialized into a text file, and contacts from a file can be read back into another address book.

### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with:
  - `writeContactsToFile(String addressBookName, String filePath)`
  - `readContactsFromFile(String addressBookName, String filePath)`
- Used `Files.write(...)` to save serialized contacts into a text file
- Used `Files.readAllLines(...)` to read contacts back from the file
- Added `serializeContact(...)` and `deserializeContact(...)` helpers for conversion
- Stored contact fields in pipe-separated format
- Returned `false` or `-1` for invalid address books or file I/O failures
- Ignored malformed lines while continuing to process valid records

### Test Coverage

- Added UC13 coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that contacts can be written successfully to a temporary file
- Verified that contacts can be read back into another address book
- Confirmed that imported contact count matches the original data

### Outcome

UC13 successfully adds plain text file persistence, allowing address book entries to be saved and restored outside application memory.

---

## Planned Use Cases

- `UC14` CSV File Read and Write
- `UC15` JSON File Read and Write
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

## Project Structure

```text
AddressBookApp
|-- src
|   |-- main/java/com/addressbookapp
|   |   |-- controller
|   |   |-- model
|   |   |-- service
|   |   -- AddressBookApplication.java
|   |-- resources
|   |   -- application.properties
|   -- test/java/com/addressbookapp
|       |-- controller
|       |-- service
|       -- AddressBookApplicationTests.java
|-- pom.xml
|-- mvnw
|-- mvnw.cmd
-- README.md
```

---

## Author

**Rudresh Sharma**
