package com.addressbookapp.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

    private AddressBook addressBook;

    @BeforeEach
    void setUp() {
        addressBook = new AddressBook();
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

        addressBook.addContact(contact);

        assertEquals(1, addressBook.getContactList().size());
        assertEquals("Aman",
                addressBook.getContactList().get(0).getFirstName());
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

        addressBook.addContact(contact);

        // Simulated console input
        String simulatedInput =
                "New Address\n" +
                "Mumbai\n" +
                "Maharashtra\n" +
                "400001\n" +
                "8888888888\n" +
                "newravi@gmail.com\n";

        Scanner scanner =
                new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        addressBook.editContact("Ravi", scanner);

        Contact updatedContact = addressBook.getContactList().get(0);

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

        addressBook.addContact(contact);

        String simulatedInput =
                "Address\nCity\nState\n123\nPhone\nEmail\n";

        Scanner scanner =
                new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        addressBook.editContact("Unknown", scanner);

        // List size should remain 1
        assertEquals(1, addressBook.getContactList().size());
        assertEquals("Test",
                addressBook.getContactList().get(0).getFirstName());
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

        addressBook.addContact(contact);

        boolean deleted = addressBook.deleteContact("Ravi");

        assertTrue(deleted);
        assertEquals(0, addressBook.getContactList().size());
    }

    @Test
    void givenNonExistingContact_whenDeleteAttempted_shouldReturnFalse() {

        boolean deleted = addressBook.deleteContact("Unknown");

        assertFalse(deleted);
    }
}