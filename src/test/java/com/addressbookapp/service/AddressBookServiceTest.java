package com.addressbookapp.service;

import com.addressbookapp.model.Contact;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressBookServiceTest {

    private AddressBookService addressBookService;

    @BeforeEach
    void setUp() {
        addressBookService = new AddressBookService();
    }

    // ---------------- UC1 ----------------

    @Test
    void testCreateContact() {

        Contact contact = new Contact(
                "Rudresh",
                "Sharma",
                "ABC Street",
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

    // ---------------- UC2 ----------------

    @Test
    void testAddContact() {

        Contact contact = new Contact(
                "Aman",
                "Verma",
                "XYZ Street",
                "Bhopal",
                "MP",
                "462001",
                "8888888888",
                "aman@gmail.com"
        );

        addressBookService.addContact(contact);

        assertEquals(1, addressBookService.getAllContacts().size());
        assertEquals("Aman", addressBookService.getAllContacts().get(0).getFirstName());
    }

//    // ---------------- UC3 ----------------
//
//    @Test
//    void testEditExistingContact() {
//
//        Contact contact = new Contact(
//                "Ravi",
//                "Kumar",
//                "Old Address",
//                "Delhi",
//                "Delhi",
//                "110001",
//                "7777777777",
//                "ravi@gmail.com"
//        );
//
//        addressBook.addContact(contact);
//
//        boolean updated = addressBook.editContact("Ravi", "Mumbai");
//
//        assertTrue(updated);
//        assertEquals("Mumbai", addressBook.getContactList().get(0).getCity());
//    }
//
//    @Test
//    void testEditNonExistingContact() {
//
//        boolean updated = addressBook.editContact("Unknown", "Pune");
//
//        assertFalse(updated);
//    }
}
