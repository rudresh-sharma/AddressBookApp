package com.addressbookapp.service;

import com.addressbookapp.model.Contact;
import com.addressbookapp.repository.AddressBookRepository;
import com.addressbookapp.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AddressBookDbServiceTest {

    @Autowired
    private AddressBookDbService dbService;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private ContactRepository contactRepository;

    @BeforeEach
    void setUp() {
        contactRepository.deleteAll();
        addressBookRepository.deleteAll();
    }

    @Test
    void givenDbEntries_whenRetrieved_shouldReturnAllContacts() {
        dbService.saveAddressBookWithContacts("PersonalDb", List.of(
                new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"),
                new Contact("Aman", "Verma", "Street 2", "Bhopal", "MP", "462001", "9000000002", "aman@gmail.com")
        ));

        List<Contact> contacts = dbService.retrieveAllEntriesFromDb();

        assertEquals(2, contacts.size());
        assertTrue(contacts.stream().anyMatch(contact -> "Rudresh".equals(contact.getFirstName())));
        assertTrue(contacts.stream().anyMatch(contact -> "Aman".equals(contact.getFirstName())));
    }

    @Test
    void givenPersonContact_whenUpdated_shouldSyncWithDb() {
        dbService.saveAddressBookWithContacts("SyncDb", List.of(
                new Contact("Rudresh", "Sharma", "Old Address", "Indore", "MP", "452001", "9000000001", "old@mail.com")
        ));

        Contact updated = new Contact("Rudresh", "Sharma", "New Address", "Pune", "MH", "411001", "9000000999", "new@mail.com");
        boolean updatedInDb = dbService.updateContactInDb("SyncDb", "Rudresh", updated);
        Contact fromDb = dbService.getContactFromDb("SyncDb", "Rudresh");

        assertTrue(updatedInDb);
        assertEquals(updated, fromDb);
        assertTrue(dbService.isMemoryInSyncWithDb("SyncDb", "Rudresh", updated));
    }

    @Test
    void givenContacts_whenRetrievedByDateRange_shouldReturnOnlyMatchingPeriod() {
        dbService.saveAddressBookWithContacts("DateBook", List.of(
                new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"),
                new Contact("Aman", "Verma", "Street 2", "Bhopal", "MP", "462001", "9000000002", "aman@gmail.com")
        ));

        List<Contact> todayContacts = dbService.retrieveContactsAddedBetween(LocalDate.now(), LocalDate.now());
        List<Contact> oldRangeContacts = dbService.retrieveContactsAddedBetween(
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 2)
        );

        assertTrue(todayContacts.size() >= 2);
        assertEquals(0, oldRangeContacts.size());
    }

    @Test
    void givenContacts_whenCountedByCityAndStateInDb_shouldReturnExpectedCounts() {
        dbService.saveAddressBookWithContacts("CountBook", List.of(
                new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"),
                new Contact("Aman", "Verma", "Street 2", "Indore", "MP", "452002", "9000000002", "aman@gmail.com"),
                new Contact("Ravi", "Kumar", "Street 3", "Delhi", "Delhi", "110001", "9000000003", "ravi@gmail.com")
        ));

        Map<String, Long> cityCounts = dbService.getPersonCountByCityFromDb();
        Map<String, Long> stateCounts = dbService.getPersonCountByStateFromDb();

        assertEquals(2L, cityCounts.get("Indore"));
        assertEquals(1L, cityCounts.get("Delhi"));
        assertEquals(2L, stateCounts.get("MP"));
        assertEquals(1L, stateCounts.get("Delhi"));
    }

    @Test
    void givenNewContact_whenAddedToDb_shouldPersistWithTransactionAcrossTables() {
        Contact contact = new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com");

        boolean added = dbService.addContactToAddressBookDb("TransactionalBook", contact);
        Contact fromDb = dbService.getContactFromDb("TransactionalBook", "Rudresh");

        assertTrue(added);
        assertEquals(contact, fromDb);
        assertTrue(addressBookRepository.findByName("TransactionalBook").isPresent());
        assertEquals(1, contactRepository.count());
    }

    @Test
    void givenDuplicateContact_whenAddedToDb_shouldNotPersistAgain() {
        Contact contact = new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com");

        assertTrue(dbService.addContactToAddressBookDb("TransactionalBookDup", contact));
        assertFalse(dbService.addContactToAddressBookDb("TransactionalBookDup", contact));
        assertEquals(1, contactRepository.count());
    }

    @Test
    void givenMultipleContacts_whenAddedWithThreads_shouldPersistAllUniqueEntries() {
        List<Contact> contacts = List.of(
                new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"),
                new Contact("Aman", "Verma", "Street 2", "Bhopal", "MP", "462001", "9000000002", "aman@gmail.com"),
                new Contact("Ravi", "Kumar", "Street 3", "Delhi", "Delhi", "110001", "9000000003", "ravi@gmail.com")
        );

        int addedCount = dbService.addContactsToAddressBookDbUsingThreads("ThreadBook", contacts);

        assertEquals(3, addedCount);
        assertEquals(3, contactRepository.count());
        assertTrue(addressBookRepository.findByName("ThreadBook").isPresent());
    }
}
