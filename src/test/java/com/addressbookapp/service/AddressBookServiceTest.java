package com.addressbookapp.service;

import java.util.List;
import java.util.Map;

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

    @Test
    void givenContactDetails_whenCreated_shouldHaveCorrectValues() {
        Contact contact = new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9999999999", "rudresh@gmail.com");
        assertEquals("Rudresh", contact.getFirstName());
        assertEquals("Indore", contact.getCity());
        assertEquals("MP", contact.getState());
    }

    @Test
    void givenContact_whenAdded_shouldIncreaseListSize() {
        service.addContact("Default", new Contact("Aman", "Verma", "Street 2", "Bhopal", "MP", "462001", "8888888888", "aman@gmail.com"));
        assertEquals(1, service.getContacts("Default").size());
    }

    @Test
    void givenExistingContact_whenEdited_shouldUpdateDetails() {
        service.addContact("Default", new Contact("Ravi", "Kumar", "Old Address", "Delhi", "Delhi", "110001", "7777777777", "ravi@gmail.com"));
        boolean edited = service.editContact("Default", "Ravi", new Contact("Ravi", "Kumar", "New Address", "Mumbai", "Maharashtra", "400001", "8888888888", "newravi@gmail.com"));
        assertTrue(edited);
        assertEquals("Mumbai", service.getContacts("Default").get(0).getCity());
    }

    @Test
    void givenNonExistingContact_whenEditAttempted_shouldNotChangeList() {
        service.addContact("Default", new Contact("Test", "User", "Address", "City", "State", "123456", "1111111111", "test@gmail.com"));
        assertFalse(service.editContact("Default", "Unknown", new Contact()));
    }

    @Test
    void givenExistingContact_whenDeleted_shouldRemoveFromList() {
        service.addContact("Default", new Contact("Ravi", "Kumar", "Address", "City", "State", "123456", "9999999999", "ravi@gmail.com"));
        assertTrue(service.deleteContact("Default", "Ravi"));
        assertEquals(0, service.getContacts("Default").size());
    }

    @Test
    void givenNonExistingContact_whenDeleteAttempted_shouldReturnFalse() {
        assertFalse(service.deleteContact("Default", "Unknown"));
    }

    @Test
    void givenMultipleContacts_whenAdded_shouldStoreAll() {
        service.addContacts("Default", List.of(
                new Contact("Ravi", "Kumar", "Addr1", "Delhi", "Delhi", "110001", "9999999999", "ravi@gmail.com"),
                new Contact("Aman", "Verma", "Addr2", "Indore", "MP", "452001", "8888888888", "aman@gmail.com")
        ));
        assertEquals(2, service.getContacts("Default").size());
    }

    @Test
    void givenUniqueName_whenAddressBookAdded_shouldStoreInMap() {
        assertTrue(service.addAddressBook("Personal"));
        assertTrue(service.getAddressBookNames().contains("Personal"));
    }

    @Test
    void givenDuplicateName_whenAdded_shouldReturnFalse() {
        service.addAddressBook("Office");
        assertFalse(service.addAddressBook("Office"));
    }

    @Test
    void givenDuplicateContact_whenAdded_shouldNotBeAdded() {
        service.addContact("Default", new Contact("Ravi", "Kumar", "Addr", "City", "State", "123456", "9999999999", "ravi@gmail.com"));
        boolean added = service.addContact("Default", new Contact("Ravi", "Kumar", "Addr2", "City2", "State2", "654321", "8888888888", "ravi2@gmail.com"));
        assertFalse(added);
        assertEquals(1, service.getContacts("Default").size());
    }

    @Test
    void givenMultipleAddressBooks_whenSearchAndGroup_shouldReturnExpectedResults() {
        service.addAddressBook("Personal");
        service.addAddressBook("Office");
        service.addContact("Personal", new Contact("Ravi", "Kumar", "Addr", "Delhi", "Delhi", "110001", "9999999999", "ravi@gmail.com"));
        service.addContact("Office", new Contact("Aman", "Verma", "Addr2", "Delhi", "Delhi", "110002", "8888888888", "aman@gmail.com"));

        List<Contact> cityResult = service.searchByCity("Delhi");
        List<Contact> stateResult = service.searchByState("Delhi");
        Map<String, List<Contact>> groupedCity = service.getPersonsGroupedByCity();
        Map<String, List<Contact>> groupedState = service.getPersonsGroupedByState();

        assertEquals(2, cityResult.size());
        assertEquals(2, stateResult.size());
        assertEquals(2, groupedCity.get("Delhi").size());
        assertEquals(2, groupedState.get("Delhi").size());
    }
}
