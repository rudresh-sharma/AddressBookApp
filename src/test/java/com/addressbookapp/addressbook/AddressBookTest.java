package com.addressbookapp.addressbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

    private AddressBook addressBook;

    @BeforeEach
    void setUp() {
        addressBook = new AddressBook();
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

        addressBook.addContact(contact);

        assertEquals(1, addressBook.getContactList().size());
        assertEquals("Aman", addressBook.getContactList().get(0).getFirstName());
    }

   
}