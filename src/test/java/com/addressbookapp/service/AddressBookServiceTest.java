package com.addressbookapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import com.addressbookapp.model.Contact;
import static org.junit.jupiter.api.Assertions.*;

class AddressBookServiceTest {

    private AddressBookService addressBookService;

    @BeforeEach
    void setUp() {
        addressBookService = new AddressBookService();
    }

    // =========================
    // UC1 - Create Contact
    // =========================
    @Test
    void givenContactDetails_whenCreated_shouldHaveCorrectValues() {

        Contact contact = new Contact(
                "Rudresh",
                "Sharma",
                "Street 1",
                "Indore",
                "MP",
                "452001",
                "9999999999",
                "rudresh@gmail.com"
        );

        assertEquals("Rudresh", contact.getFirstName());
        assertEquals("Indore", contact.getCity());
        assertEquals("MP", contact.getState());
    }

    // =========================
    // UC2 - Add Contact
    // =========================
    @Test
    void givenContact_whenAdded_shouldIncreaseListSize() {

        Contact contact = new Contact(
                "Aman",
                "Verma",
                "Street 2",
                "Bhopal",
                "MP",
                "462001",
                "8888888888",
                "aman@gmail.com"
        );

        addressBookService.addContact(contact);

        assertEquals(1, addressBookService.getContactList().size());
        assertEquals("Aman",
                addressBookService.getContactList().get(0).getFirstName());
    }

    // =========================
    // UC3 - Edit Existing Contact
    // =========================
    @Test
    void givenExistingContact_whenEdited_shouldUpdateDetails() {

        Contact contact = new Contact(
                "Ravi",
                "Kumar",
                "Old Address",
                "Delhi",
                "Delhi",
                "110001",
                "7777777777",
                "ravi@gmail.com"
        );

        addressBookService.addContact(contact);

        Contact updatedData = new Contact(
                "Ravi", "Kumar", "New Address",
                "Mumbai", "Maharashtra", "400001",
                "8888888888", "newravi@gmail.com"
        );

        boolean edited = addressBookService.editContact("Ravi", updatedData);
        Contact updatedContact = addressBookService.getContactList().get(0);

        assertTrue(edited);
        assertEquals("New Address", updatedContact.getAddress());
        assertEquals("Mumbai", updatedContact.getCity());
        assertEquals("Maharashtra", updatedContact.getState());
        assertEquals("400001", updatedContact.getZip());
        assertEquals("8888888888", updatedContact.getPhoneNumber());
        assertEquals("newravi@gmail.com", updatedContact.getEmail());
    }

    // =========================
    // UC3 - Edit Non Existing Contact
    // =========================
    @Test
    void givenNonExistingContact_whenEditAttempted_shouldNotChangeList() {

        Contact contact = new Contact(
                "Test",
                "User",
                "Address",
                "City",
                "State",
                "123456",
                "1111111111",
                "test@gmail.com"
        );

        addressBookService.addContact(contact);

        Contact updatedData = new Contact(
                "Test", "User", "Address 2",
                "City 2", "State 2", "654321",
                "2222222222", "test2@gmail.com"
        );

        boolean edited = addressBookService.editContact("Unknown", updatedData);

        // List size should remain 1
        assertFalse(edited);
        assertEquals(1, addressBookService.getContactList().size());
        assertEquals("Test",
                addressBookService.getContactList().get(0).getFirstName());
    }
    
    
    // UC -4 TEST CASES
    
    @Test
    void givenExistingContact_whenDeleted_shouldRemoveFromList() {

        Contact contact = new Contact(
                "Ravi",
                "Kumar",
                "Address",
                "City",
                "State",
                "123456",
                "9999999999",
                "ravi@gmail.com"
        );

        addressBookService.addContact(contact);

        boolean deleted = addressBookService.deleteContact("Ravi");

        assertTrue(deleted);
        assertEquals(0, addressBookService.getContactList().size());
    }

    @Test
    void givenNonExistingContact_whenDeleteAttempted_shouldReturnFalse() {

        boolean deleted = addressBookService.deleteContact("Unknown");

        assertFalse(deleted);
    }
    
    
    
    // =======================================
    // 				UC-5
    // =========== ============================
    
    @Test
    void givenMultipleContacts_whenAdded_shouldStoreAll() {

        Contact contact1 = new Contact(
                "Ravi", "Kumar", "Addr1",
                "Delhi", "Delhi", "110001",
                "9999999999", "ravi@gmail.com"
        );

        Contact contact2 = new Contact(
                "Aman", "Verma", "Addr2",
                "Indore", "MP", "452001",
                "8888888888", "aman@gmail.com"
        );

        addressBookService.addContacts(List.of(contact1, contact2));

        assertEquals(2, addressBookService.getContactList().size());
    }
    
    
    
    
}
