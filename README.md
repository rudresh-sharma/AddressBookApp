# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case introduces one focused capability to the Address Book system, helping the project grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp is a progressive contact management project built one use case at a time.  
The application starts with the core `Contact` model and expands through small, well-defined features such as adding contacts, editing details, deleting records, managing multiple contacts, organizing multiple address books, preventing duplicates, searching contacts, and eventually supporting sorting, file handling, and persistence.

This development approach keeps the system structured, easy to test, and simple to extend as the project evolves.

---

## Use Case Documentation Approach

Each implemented use case is documented using a consistent professional structure:

- Objective
- Functional scope
- Implementation details
- Test coverage
- Outcome

This format keeps the README clear, maintainable, and suitable for review or submission.

---

## Implemented Use Cases

### UC1 - Create Contact

#### Objective

To define the foundational domain object of the Address Book system by creating a `Contact` model that stores personal and communication details in a structured form.

#### Functional Scope

UC1 introduces the basic contact entity used throughout the application.  
This model serves as the base for all later address book operations.

#### Contact Attributes

The `Contact` model includes:

- First name
- Last name
- Address
- City
- State
- Zip
- Phone number
- Email

#### Implementation Details

- Implemented the `Contact` model in [Contact.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/model/Contact.java)
- Used Lombok annotations to reduce boilerplate code
- Added custom `equals()` and `hashCode()` logic in the current version to support duplicate detection by first name and last name
- Designed the model to act as the base entity for future use cases

#### Test Coverage

- Added UC1 validation in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified object creation
- Confirmed that field values are stored and accessed correctly

#### Outcome

UC1 successfully establishes the contact representation required for all later address book features.

---

### UC2 - Add Contact to Address Book

#### Objective

To allow a contact to be added to the Address Book so that the system can begin storing and managing contact records.

#### Functional Scope

UC2 extends the project from a standalone contact model to a structure capable of maintaining address book entries.  
It introduces the first core address book operation: adding a contact entry.

#### Implementation Details

- Implemented contact storage support in [AddressBook.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/model/AddressBook.java)
- Added an internal `List<Contact>` for maintaining address book entries
- Implemented `addContact(Contact contact)` to store a new contact
- Exposed `getContactList()` as a read-only view of the stored contacts

#### Test Coverage

- Added UC2 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that adding a contact increases the list size
- Confirmed that the stored contact matches the inserted data

#### Outcome

UC2 successfully introduces contact storage and enables the system to maintain address book entries.

---

### UC3 - Edit Existing Contact

#### Objective

To allow an existing contact in the Address Book to be updated so that stored details remain accurate and manageable over time.

#### Functional Scope

UC3 introduces contact editing capability.  
The feature locates a contact by first name and updates editable fields such as address, city, state, zip, phone number, and email.

#### Implementation Details

- Extended [AddressBook.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/model/AddressBook.java) with `editContact(String firstName, Contact updatedContact)`
- Implemented search logic using the contact's first name
- Updated address, city, state, zip, phone number, and email when a match is found
- Returned `true` when the contact is updated successfully
- Returned `false` when no matching contact exists

#### Test Coverage

- Added UC3 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that an existing contact is updated correctly
- Confirmed that editable fields reflect the new values after update
- Verified that editing a non-existing contact returns `false`
- Confirmed that the contact list remains unchanged when no matching entry is found

#### Outcome

UC3 successfully adds update functionality and allows existing contact details to be modified safely.

---

### UC4 - Delete Contact

#### Objective

To allow an existing contact to be removed from the Address Book when the entry is no longer needed.

#### Functional Scope

UC4 introduces deletion capability.  
The feature searches the address book by first name and removes the matching contact from the list.

#### Implementation Details

- Extended [AddressBook.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/model/AddressBook.java) with `deleteContact(String firstName)`
- Implemented deletion using `removeIf` on the internal contact list
- Matched contacts case-insensitively using the first name
- Returned `true` when a contact was removed successfully
- Returned `false` when no matching contact was found

#### Test Coverage

- Added UC4 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that an existing contact is removed from the list
- Confirmed that the contact count decreases after deletion
- Verified that deleting a non-existing contact returns `false`

#### Outcome

UC4 successfully adds contact deletion support and allows the Address Book to remove outdated or unwanted entries.

---

### UC5 - Add Multiple Contacts

#### Objective

To allow multiple contacts to be added to the Address Book in a single operation, making contact entry more efficient.

#### Functional Scope

UC5 extends the address book to support bulk insertion of contacts.  
Instead of adding one contact at a time, the feature accepts a collection of contacts and stores all of them together.

#### Implementation Details

- Extended [AddressBook.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/model/AddressBook.java) with `addContacts(List<Contact> contacts)`
- Implemented iterative bulk addition so duplicate rules can still be enforced
- Returned the number of successfully added contacts in the current implementation
- Reused the existing in-memory contact list to store all entries

#### Test Coverage

- Added UC5 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that multiple contacts can be added in one call
- Confirmed that the contact list size increases correctly after bulk insertion

#### Outcome

UC5 successfully adds bulk contact insertion support and allows the Address Book to store multiple entries in a single operation.

---

### UC6 - Multiple Address Books

#### Objective

To support multiple named address books so that contacts can be organized into separate collections such as personal, office, or family.

#### Functional Scope

UC6 moves the application from managing a single contact list to managing multiple address books.  
Each address book is identified by name, and all contact operations are performed within the selected address book.

#### Implementation Details

- Introduced address book management in [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java)
- Added `Map<String, AddressBook>` to store multiple address books by name
- Implemented `addAddressBook(String name)` to create a new address book when the name is unique
- Prevented duplicate address book names by returning `false` if a name already exists
- Added `getAddressBook(String name)` and `getAddressBookNames()` for retrieval and inspection
- Updated contact operations to work against a named address book

#### Test Coverage

- Added UC6 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that a uniquely named address book can be added successfully
- Confirmed that the new address book name is stored in the service map
- Verified that adding an address book with a duplicate name returns `false`

#### Outcome

UC6 successfully introduces support for multiple named address books and provides the structure needed to organize contacts across separate collections.

---

### UC7 - Prevent Duplicate Contacts

#### Objective

To prevent duplicate contacts from being added to the same address book, ensuring that each contact entry remains unique.

#### Functional Scope

UC7 introduces duplicate-checking during contact insertion.  
When a contact with the same first name and last name already exists in the selected address book, the new entry is rejected instead of being stored again.

#### Implementation Details

- Updated [Contact.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/model/Contact.java) with custom `equals()` and `hashCode()` logic based on first name and last name
- Updated [AddressBook.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/model/AddressBook.java) so `addContact(Contact newContact)` checks for duplicates before insertion
- Implemented duplicate detection using `contactList.stream().anyMatch(contact -> contact.equals(newContact))`
- Returned `false` when a duplicate contact is found
- Returned `true` only when the contact is actually added
- Updated [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) so `addContact(String addressBookName, Contact contact)` now returns a boolean result

#### Test Coverage

- Added UC7 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that adding a duplicate contact returns `false`
- Confirmed that the address book size remains unchanged when a duplicate is rejected

#### Outcome

UC7 successfully prevents duplicate contacts from being stored in the same address book and improves data consistency.

---

### UC8 - Search Person by City or State

#### Objective

To allow users to search contacts across address books by city or state so that matching people can be located quickly.

#### Functional Scope

UC8 introduces search capability at the service layer.  
The feature scans contacts across all stored address books and returns the entries whose city or state matches the search input.

#### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with:
  - `searchByCity(String city)`
  - `searchByState(String state)`
- Implemented search using stream operations over all address books in `addressBookMap`
- Flattened all contact lists with `flatMap(...)`
- Filtered matching contacts using case-insensitive checks on city and state
- Returned the matching contacts as a list

#### Test Coverage

- Added UC8 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that searching by city returns contacts from multiple address books when they match
- Verified that searching by state returns contacts from multiple address books when they match
- Confirmed that the result size matches the number of valid matches found

#### Outcome

UC8 successfully adds cross-address-book search by city and state, making it easier to find relevant contacts from the full collection.

---

## Planned Use Cases

- `UC9` - View Persons by City or State
- `UC10` - Count Contacts by City or State
- `UC11` - Sort Contacts Alphabetically
- `UC12` - Sort Contacts by City, State, or Zip
- `UC13` - Write Address Book to File
- `UC14` - Read Address Book from File
- `UC15` - Count Contacts in File
- `UC16` - Write Contacts to CSV File
- `UC17` - Read Contacts from CSV File
- `UC18` - Write Contacts to JSON File
- `UC19` - Read Contacts from JSON File
- `UC20` - Add Contacts Using Threads
- `UC21` - Measure Time for Threaded Contact Addition
- `UC22` - Add Multiple Contacts Using Thread Pools
- `UC23` - Measure Thread Pool Performance
- `UC24` - Store Address Book in Database
- `UC25` - Retrieve Contacts from Database

---

## Tech Stack

- **Java 17** for application development
- **Spring Boot** for project structure and configuration
- **Maven** for dependency management and build automation
- **JUnit 5** for test-driven development
- **Lombok** for reducing boilerplate code

---

## Build And Run

Build the project:

```bash
./mvnw clean install
```

Run tests:

```bash
./mvnw test
```

Run the application:

```bash
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
|
|-- src
|   |-- main
|   |   |-- java
|   |   |   -- com
|   |   |      -- addressbookapp
|   |   |         |-- controller
|   |   |         |-- model
|   |   |         |-- service
|   |   |         -- AddressBookApplication.java
|   |   -- resources
|   |      -- application.properties
|   -- test
|      -- java
|         -- com
|            -- addressbookapp
|               |-- controller
|               |-- service
|               -- AddressBookApplicationTests.java
|-- pom.xml
|-- mvnw
|-- mvnw.cmd
-- README.md
```

---

## Development Approach

The project follows a disciplined TDD workflow:

- Write the test first
- Implement the minimum code necessary
- Refactor safely after tests pass
- Document progress use case by use case

This process supports clarity, correctness, and steady growth of the application.

---

## Author

**Rudresh Sharma**

---

<div align="center">
Incrementally developed with Test-Driven Development and use-case-based implementation.
</div>
