# AddressBookApp

> A Java Spring Boot based Address Book application developed incrementally using Test-Driven Development (TDD). The project is organized use case by use case, with each feature implemented on a dedicated branch before being finalized as part of the overall system.

## Overview

AddressBookApp is a progressive contact management project designed to demonstrate structured software development through small, testable use cases.

Across the feature branches, the application evolves from a simple `Contact` model into a more complete system that supports:

- Contact creation, update, and deletion
- Duplicate prevention
- Multiple address books
- Search, grouping, counting, and sorting
- File persistence using text, CSV, and JSON
- Database integration using Spring Data JPA
- Transactional and threaded database operations
- JSON Server synchronization with REST-style integration tests

The `main` branch acts as the repository entry point, while the detailed implementation work is maintained across the `feature/UC...` branches.

---

## Branch Structure

This repository is organized by use case branches.

Examples:

- `feature/UC1-create-contact`
- `feature/UC6-multiple-addressbooks`
- `feature/UC13-file-io-read-write-address-book`
- `feature/UC16-jdbc-section-jpa-db-retrieval`
- `feature/UC22-read-entries-jsonserver-restassured-sync-memory`
- `feature/UC25-delete-entry-jsonserver-restassured-sync-memory`

Each branch represents a focused stage of development for one use case or feature milestone.

---

## Covered Use Cases

The project work spans `UC1` through `UC25`, covering:

- Core contact modeling
- Address book operations
- Search and reporting
- Sorting
- File I/O
- CSV and JSON serialization
- Database retrieval and updates
- Transaction handling
- Multi-threaded persistence
- JSON Server synchronization

---

## Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database**
- **Maven**
- **JUnit 5**
- **Lombok**
- **OpenCSV**
- **Gson**
- **Rest Assured**

---

## Development Approach

This project follows a disciplined TDD workflow:

- Write tests first
- Implement the required behavior
- Refactor while preserving correctness
- Document progress use case by use case

This approach keeps the project modular, understandable, and easy to expand.

---

## How To Explore

To view a specific use case implementation, check out the corresponding feature branch. For example:

```bash
git checkout feature/UC10-count-contacts-by-city-state-streams
```

or

```bash
git checkout feature/UC25-delete-entry-jsonserver-restassured-sync-memory
```

---

## Author

**Rudresh Sharma**
