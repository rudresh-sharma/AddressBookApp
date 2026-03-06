package com.addressbookapp.service;

import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;

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
        Map<String, Long> cityCount = service.getPersonCountByCity();
        Map<String, Long> stateCount = service.getPersonCountByState();

        assertEquals(2, cityResult.size());
        assertEquals(2, stateResult.size());
        assertEquals(2, groupedCity.get("Delhi").size());
        assertEquals(2, groupedState.get("Delhi").size());
        assertEquals(2L, cityCount.get("Delhi"));
        assertEquals(2L, stateCount.get("Delhi"));
    }

    @Test
    void givenContacts_whenSortedByName_shouldReturnAlphabeticalOrder() {
        service.addContact("Default", new Contact("Ravi", "Kumar", "Addr1", "Delhi", "Delhi", "110001", "9999999999", "ravi@gmail.com"));
        service.addContact("Default", new Contact("Aman", "Verma", "Addr2", "Indore", "MP", "452001", "8888888888", "aman@gmail.com"));
        service.addContact("Default", new Contact("Ankit", "Sharma", "Addr3", "Bhopal", "MP", "462001", "7777777777", "ankit@gmail.com"));

        List<Contact> sorted = service.getContactsSortedByName("Default");

        assertEquals("Aman", sorted.get(0).getFirstName());
        assertEquals("Ankit", sorted.get(1).getFirstName());
        assertEquals("Ravi", sorted.get(2).getFirstName());
    }

    @Test
    void givenContacts_whenSortedByCityStateZip_shouldReturnExpectedOrder() {
        service.addContact("Default", new Contact("Ravi", "Kumar", "Addr1", "Delhi", "Delhi", "110002", "9999999999", "ravi@gmail.com"));
        service.addContact("Default", new Contact("Aman", "Verma", "Addr2", "Indore", "MP", "452001", "8888888888", "aman@gmail.com"));
        service.addContact("Default", new Contact("Ankit", "Sharma", "Addr3", "Bhopal", "MP", "462001", "7777777777", "ankit@gmail.com"));

        List<Contact> sortedByCity = service.getContactsSortedByCity("Default");
        List<Contact> sortedByState = service.getContactsSortedByState("Default");
        List<Contact> sortedByZip = service.getContactsSortedByZip("Default");

        assertEquals("Bhopal", sortedByCity.get(0).getCity());
        assertEquals("Delhi", sortedByCity.get(1).getCity());
        assertEquals("Indore", sortedByCity.get(2).getCity());

        assertEquals("Delhi", sortedByState.get(0).getState());
        assertEquals("MP", sortedByState.get(1).getState());
        assertEquals("MP", sortedByState.get(2).getState());

        assertEquals("110002", sortedByZip.get(0).getZip());
        assertEquals("452001", sortedByZip.get(1).getZip());
        assertEquals("462001", sortedByZip.get(2).getZip());
    }

    @Test
    void givenAddressBook_whenWrittenAndReadViaFileIo_shouldPersistContacts() throws Exception {
        service.addContact("Default", new Contact("Aman", "Verma", "Street 1, Sector A", "Indore", "MP", "452001", "9000000001", "aman@gmail.com"));
        service.addContact("Default", new Contact("Ravi", "Kumar", "Street 2", "Delhi", "Delhi", "110001", "9000000002", "ravi@gmail.com"));
        service.addAddressBook("Imported");

        Path tempFile = Files.createTempFile("addressbook-uc14-", ".csv");
        try {
            boolean written = service.writeContactsToFile("Default", tempFile.toString());
            int addedCount = service.readContactsFromFile("Imported", tempFile.toString());

            assertTrue(written);
            assertEquals(2, addedCount);
            assertEquals(2, service.getContacts("Imported").size());
            assertEquals("Street 1, Sector A", service.getContacts("Imported").get(0).getAddress());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void givenAddressBook_whenWrittenAndReadViaJsonIo_shouldPersistContacts() throws Exception {
        service.addContact("Default", new Contact("Aman", "Verma", "Street 1, Sector A", "Indore", "MP", "452001", "9000000001", "aman@gmail.com"));
        service.addContact("Default", new Contact("Ravi", "Kumar", "Street 2", "Delhi", "Delhi", "110001", "9000000002", "ravi@gmail.com"));
        service.addAddressBook("ImportedJson");

        Path tempFile = Files.createTempFile("addressbook-uc15-", ".json");
        try {
            boolean written = service.writeContactsToJsonFile("Default", tempFile.toString());
            int addedCount = service.readContactsFromJsonFile("ImportedJson", tempFile.toString());

            assertTrue(written);
            assertEquals(2, addedCount);
            assertEquals(2, service.getContacts("ImportedJson").size());
            assertEquals("Street 1, Sector A", service.getContacts("ImportedJson").get(0).getAddress());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
