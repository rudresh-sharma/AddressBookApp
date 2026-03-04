package com.addressbookapp.service;

import java.util.List;

import com.addressbookapp.model.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookServiceTest {

    private AddressBookService service;

    @BeforeEach
    void setUp() {
        service = new AddressBookService();
        service.addAddressBook("Default");
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

        service.addContact("Default", contact);

        assertEquals(1, service.getContacts("Default").size());
        assertEquals("Aman",
                service.getContacts("Default").get(0).getFirstName());
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

        service.addContact("Default", contact);
        Contact updated = new Contact(
                "Ravi", "Kumar", "New Address",
                "Mumbai", "Maharashtra", "400001",
                "8888888888", "newravi@gmail.com"
        );
        boolean edited = service.editContact("Default", "Ravi", updated);
        Contact updatedContact = service.getContacts("Default").get(0);

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

        service.addContact("Default", contact);
        Contact updated = new Contact(
                "Test", "User", "Address2",
                "City2", "State2", "654321",
                "2222222222", "test2@gmail.com"
        );
        boolean edited = service.editContact("Default", "Unknown", updated);

        // List size should remain 1
        assertFalse(edited);
        assertEquals(1, service.getContacts("Default").size());
        assertEquals("Test",
                service.getContacts("Default").get(0).getFirstName());
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

        service.addContact("Default", contact);

        boolean deleted = service.deleteContact("Default", "Ravi");

        assertTrue(deleted);
        assertEquals(0, service.getContacts("Default").size());
    }

    @Test
    void givenNonExistingContact_whenDeleteAttempted_shouldReturnFalse() {

        boolean deleted = service.deleteContact("Default", "Unknown");

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

        service.addContacts("Default", List.of(contact1, contact2));

        assertEquals(2, service.getContacts("Default").size());
    }
    
    
    /* =====================================
     * 			UC - 6
     * ====================================
     */
    		
    
    @Test
    void givenUniqueName_whenAddressBookAdded_shouldStoreInMap() {

        boolean added = service.addAddressBook("Personal");

        assertTrue(added);
        assertTrue(service.getAddressBookNames().contains("Personal"));
    }

    @Test
    void givenDuplicateName_whenAdded_shouldReturnFalse() {

        service.addAddressBook("Office");
        boolean addedAgain = service.addAddressBook("Office");

        assertFalse(addedAgain);
    }
    
}
