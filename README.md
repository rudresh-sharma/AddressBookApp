# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, allowing the project to grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp is built one use case at a time, starting from the core `Contact` model and expanding into contact management, duplicate prevention, multiple address books, search, grouping, counting, sorting, and file persistence.

At this stage, the project supports CSV-based persistence using OpenCSV.

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

---

## UC14 - CSV File I/O

### Objective

To persist address book contacts in CSV format and restore them back into an address book while handling field separators correctly.

### Functional Scope

UC14 replaces simple text-based persistence with CSV-based read and write support.  
Contacts can be exported to a CSV file and imported back without breaking fields such as addresses that contain commas.

### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with CSV-backed implementations of:
  - `writeContactsToFile(String addressBookName, String filePath)`
  - `readContactsFromFile(String addressBookName, String filePath)`
- Used `CSVWriter` from OpenCSV to write rows safely
- Used `CSVReader` from OpenCSV to read rows safely
- Added `deserializeContact(String[] row)` to map CSV rows into `Contact`
- Returned `false` or `-1` for invalid address books or CSV/file read failures
- Ignored malformed rows while continuing valid imports

### Test Coverage

- Added UC14 coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that contacts can be written to CSV and read back into another address book
- Confirmed that comma-containing addresses are preserved correctly after round-trip import
- Verified imported contact count and content correctness

### Outcome

UC14 successfully adds CSV-based persistence using OpenCSV, making file import and export safer and more reliable than manual delimiter handling.

---

## Planned Use Cases

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
- **OpenCSV**

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
