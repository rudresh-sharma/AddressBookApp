package com.addressbookapp.service;

import com.addressbookapp.model.Contact;
import com.addressbookapp.repository.AddressBookRepository;
import com.addressbookapp.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
