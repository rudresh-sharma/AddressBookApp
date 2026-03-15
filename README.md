# AddressBookApp

> A Spring Boot based Java application developed incrementally using Test-Driven Development (TDD). Each use case adds one focused capability to the Address Book system, helping the project grow in a clean, testable, and maintainable way.

## Overview

AddressBookApp is a progressive contact management project built use case by use case.  
The application begins with the core `Contact` model and expands through small, well-defined features such as adding contacts, editing records, deleting entries, searching data, sorting information, and eventually supporting persistence.

This development style keeps the project structured and makes each stage easy to understand, test, and document.

---

## Use Case Documentation Approach

Each implemented use case is documented using a consistent structure:

- Objective
- Functional scope
- Implementation details
- Test coverage
- Outcome

This format makes the project suitable for academic submission, portfolio presentation, and future extension.

---

## Implemented Use Cases

### UC1 - Create Contact

#### Objective

To define the basic domain object of the Address Book system by introducing a `Contact` model that stores personal and communication details in a structured form.

#### Functional Scope

UC1 creates the foundation of the application by modelling one contact entry.  
This object serves as the base for all later operations in the Address Book.

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
- Used Lombok annotations to generate constructors and accessor methods
- Kept the model focused and reusable for future use cases

#### Test Coverage

- Added UC1 validation in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified successful object creation
- Confirmed correct storage and retrieval of field values

#### Outcome

UC1 establishes the core `Contact` representation required for all future Address Book features.

---

### UC2 - Add Contact to Address Book

#### Objective

To allow a contact to be added to the Address Book so that the system can begin storing and managing multiple contact entries.

#### Functional Scope

UC2 extends the project from a standalone contact model to a basic service capable of maintaining contact records.  
It introduces the first business operation in the application: adding a contact to the address book collection.

#### Implementation Details

- Implemented contact storage in [AddressBookService.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/main/java/com/addressbookapp/service/AddressBookService.java)
- Added an internal `List<Contact>` to maintain address book entries
- Implemented `addContact(Contact contact)` to insert a new contact into the list
- Added `getContactList()` to provide a read-only view of stored contacts using `Collections.unmodifiableList`

#### Test Coverage

- Added UC2 test coverage in [AddressBookServiceTest.java](/c:/Users/ASUS/OneDrive/Desktop/AddressBookApp/src/test/java/com/addressbookapp/service/AddressBookServiceTest.java)
- Verified that a contact is successfully added to the service
- Confirmed that the contact count increases after insertion
- Confirmed that the stored contact data matches the added entry

#### Outcome

UC2 successfully introduces the first address book operation by enabling contact addition and retrieval from the service layer.

---

## Planned Use Cases

- `UC3` - Edit Existing Contact
- `UC4` - Delete Contact
- `UC5` - Prevent Duplicate Entries
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
- **Spring Boot** for project structure and service configuration
- **Maven** for build and dependency management
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
- Implement the minimum required code
- Refactor safely after passing tests
- Document progress use case by use case

This process helps maintain correctness, readability, and consistent project growth.

---

## Author

**Rudresh Sharma**

---

<div align="center">
Incrementally developed with Test-Driven Development and use-case-based implementation.
</div>
