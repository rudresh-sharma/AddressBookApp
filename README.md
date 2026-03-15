# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case introduces one focused capability to the Address Book system, helping the project grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp is a progressive contact management project built one use case at a time.  
The application starts with the core `Contact` model and expands through small, well-defined features such as adding contacts, editing details, deleting records, managing multiple entries, searching contacts, sorting data, and eventually supporting persistence.

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

UC2 extends the project from a standalone contact model to a service capable of maintaining a list of contacts.  
It introduces the first core address book operation: adding a contact entry.

#### Implementation Details

- Implemented contact storage in [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java)
- Added an internal `List<Contact>` for maintaining address book entries
- Implemented `addContact(Contact contact)` to store a new contact
- Provided `getContactList()` to expose a read-only view of the stored contacts

#### Test Coverage

- Added UC2 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that adding a contact increases the list size
- Confirmed that the stored contact matches the inserted data

#### Outcome

UC2 successfully introduces contact storage and enables the system to maintain address book entries through the service layer.

---

### UC3 - Edit Existing Contact

#### Objective

To allow an existing contact in the Address Book to be updated so that stored details remain accurate and manageable over time.

#### Functional Scope

UC3 introduces contact editing capability in the service layer.  
The feature locates a contact by first name and updates editable fields such as address, city, state, zip, phone number, and email.

#### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with `editContact(String firstName, Contact updatedContact)`
- Implemented search logic using the contact's first name
- Updated address, city, state, zip, phone number, and email when a match is found
- Returned `true` when the contact is updated successfully
- Returned `false` when no matching contact exists

#### Test Coverage

- Added UC3 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that an existing contact is updated correctly
- Confirmed that all editable fields reflect the new values after update
- Verified that editing a non-existing contact returns `false`
- Confirmed that the contact list remains unchanged when no matching entry is found

#### Outcome

UC3 successfully adds update functionality to the Address Book service and allows existing contact details to be modified safely.

---

### UC4 - Delete Contact

#### Objective

To allow an existing contact to be removed from the Address Book when the entry is no longer needed.

#### Functional Scope

UC4 introduces deletion capability in the service layer.  
The feature searches the address book by first name and removes the matching contact from the list.

#### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with `deleteContact(String firstName)`
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

UC5 extends the service layer to support bulk insertion of contacts.  
Instead of adding one contact at a time, the feature accepts a collection of contacts and stores all of them in the address book.

#### Implementation Details

- Extended [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java) with `addContacts(List<Contact> contacts)`
- Implemented bulk addition using `contactList.addAll(contacts)`
- Returned the list of contacts added during the operation
- Reused the existing in-memory contact list to store all entries

#### Test Coverage

- Added UC5 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that multiple contacts can be added in one call
- Confirmed that the contact list size increases correctly after bulk insertion

#### Outcome

UC5 successfully adds bulk contact insertion support and allows the Address Book to store multiple entries in a single service operation.

---

## Planned Use Cases

- `UC6` - Multiple Address Books
- `UC7` - Search Person by City or State
- `UC8` - View Persons by City or State
- `UC9` - Count Contacts by City or State
- `UC10` - Sort Contacts Alphabetically
- `UC11` - Sort Contacts by City, State, or Zip
- `UC12` - Write Address Book to File
- `UC13` - Read Address Book from File
- `UC14` - Count Contacts in File
- `UC15` - Write Contacts to CSV File
- `UC16` - Read Contacts from CSV File
- `UC17` - Write Contacts to JSON File
- `UC18` - Read Contacts from JSON File
- `UC19` - Add Contacts Using Threads
- `UC20` - Measure Time for Threaded Contact Addition
- `UC21` - Add Multiple Contacts Using Thread Pools
- `UC22` - Measure Thread Pool Performance
- `UC23` - Store Address Book in Database
- `UC24` - Retrieve Contacts from Database
- `UC25` - Update Contact in Database

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
