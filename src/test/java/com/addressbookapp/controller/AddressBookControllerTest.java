package com.addressbookapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBookDbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressBookControllerTest {

    @Autowired
    private AddressBookController controller;

    @Autowired
    private AddressBookDbService dbService;

    @Test
    void shouldSupportUc8AndUc9Views() {
        controller.addAddressBook("Personal");
        controller.addAddressBook("Office");

        controller.addContact("Personal", new Contact(
                "Ravi", "Kumar", "Addr1", "Delhi", "Delhi", "110001", "9999999999", "ravi@gmail.com"
        ));
        controller.addContact("Office", new Contact(
                "Aman", "Verma", "Addr2", "Delhi", "Delhi", "110002", "8888888888", "aman@gmail.com"
        ));

        List<Contact> byCity = controller.searchContacts("Delhi", null);
        List<Contact> byState = controller.searchContacts(null, "Delhi");
        Map<String, List<Contact>> groupedCity = controller.viewByCity();
        Map<String, List<Contact>> groupedState = controller.viewByState();
        Map<String, Long> cityCount = controller.countByCity();
        Map<String, Long> stateCount = controller.countByState();
        List<Contact> sortedByName = controller.getAllContactsSortedByName();
        List<Contact> sortedByCity = controller.getAllContactsSortedByCity();
        List<Contact> sortedByState = controller.getAllContactsSortedByState();
        List<Contact> sortedByZip = controller.getAllContactsSortedByZip();

        assertEquals(2, byCity.size());
        assertEquals(2, byState.size());
        assertEquals(2, groupedCity.get("Delhi").size());
        assertEquals(2, groupedState.get("Delhi").size());
        assertEquals(2L, cityCount.get("Delhi"));
        assertEquals(2L, stateCount.get("Delhi"));
        assertEquals("Aman", sortedByName.get(0).getFirstName());
        assertEquals("Ravi", sortedByName.get(1).getFirstName());
        assertEquals("Delhi", sortedByCity.get(0).getCity());
        assertEquals("Delhi", sortedByState.get(0).getState());
        assertEquals("110001", sortedByZip.get(0).getZip());
        assertTrue(controller.getAddressBooks().contains("Personal"));
    }

    @Test
    void shouldSupportUc13FileReadWrite() throws Exception {
        controller.addAddressBook("FileBook");
        controller.addAddressBook("ImportedFileBook");
        controller.addContact("FileBook", new Contact(
                "Aman", "Verma", "Street 1, Sector A", "Indore", "MP", "452001", "9000000001", "aman@gmail.com"
        ));
        controller.addContact("FileBook", new Contact(
                "Ravi", "Kumar", "Street 2", "Delhi", "Delhi", "110001", "9000000002", "ravi@gmail.com"
        ));

        Path tempFile = Files.createTempFile("addressbook-controller-uc14-", ".csv");
        try {
            Map<String, String> writeResponse = controller.writeContactsToFile("FileBook", tempFile.toString());
            Map<String, String> readResponse = controller.readContactsFromFile("ImportedFileBook", tempFile.toString());
            List<Contact> importedContacts = controller.getContacts("ImportedFileBook");

            assertEquals("Contacts written to file successfully", writeResponse.get("message"));
            assertEquals("Contacts read from file successfully", readResponse.get("message"));
            assertEquals("2", readResponse.get("addedCount"));
            assertEquals(2, importedContacts.size());
            assertEquals("Street 1, Sector A", importedContacts.get(0).getAddress());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void shouldSupportUc15JsonReadWrite() throws Exception {
        controller.addAddressBook("JsonBook");
        controller.addAddressBook("ImportedJsonBook");
        controller.addContact("JsonBook", new Contact(
                "Aman", "Verma", "Street 1, Sector A", "Indore", "MP", "452001", "9000000001", "aman@gmail.com"
        ));
        controller.addContact("JsonBook", new Contact(
                "Ravi", "Kumar", "Street 2", "Delhi", "Delhi", "110001", "9000000002", "ravi@gmail.com"
        ));

        Path tempFile = Files.createTempFile("addressbook-controller-uc15-", ".json");
        try {
            Map<String, String> writeResponse = controller.writeContactsToJsonFile("JsonBook", tempFile.toString());
            Map<String, String> readResponse = controller.readContactsFromJsonFile("ImportedJsonBook", tempFile.toString());
            List<Contact> importedContacts = controller.getContacts("ImportedJsonBook");

            assertEquals("Contacts written to JSON file successfully", writeResponse.get("message"));
            assertEquals("Contacts read from JSON file successfully", readResponse.get("message"));
            assertEquals("2", readResponse.get("addedCount"));
            assertEquals(2, importedContacts.size());
            assertEquals("Street 1, Sector A", importedContacts.get(0).getAddress());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void shouldSupportUc16RetrieveAllEntriesFromDb() {
        dbService.saveAddressBookWithContacts("DbBook", List.of(
                new Contact("Rudresh", "Sharma", "DB Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"),
                new Contact("Aman", "Verma", "DB Street 2", "Bhopal", "MP", "462001", "9000000002", "aman@gmail.com")
        ));

        List<Contact> dbContacts = controller.getAllEntriesFromDatabase();

        assertTrue(dbContacts.size() >= 2);
        assertTrue(dbContacts.stream().anyMatch(contact -> "Rudresh".equals(contact.getFirstName())));
        assertTrue(dbContacts.stream().anyMatch(contact -> "Aman".equals(contact.getFirstName())));
    }

    @Test
    void shouldSupportUc17UpdateAndSyncMemoryWithDb() {
        controller.addAddressBook("SyncBookUc17");
        controller.addContact("SyncBookUc17", new Contact(
                "Rudresh", "Sharma", "Old Address", "Indore", "MP", "452001", "9000000001", "old@mail.com"
        ));
        dbService.saveAddressBookWithContacts("SyncBookUc17", List.of(
                new Contact("Rudresh", "Sharma", "Old Address", "Indore", "MP", "452001", "9000000001", "old@mail.com")
        ));

        Contact updatedContact = new Contact(
                "Rudresh", "Sharma", "New Address", "Pune", "MH", "411001", "9000000999", "new@mail.com"
        );

        Map<String, String> response = controller.updateContactAndSyncWithDb("SyncBookUc17", "Rudresh", updatedContact);
        Contact dbContact = controller.getContactFromDatabase("SyncBookUc17", "Rudresh");

        assertEquals("Contact updated and memory is in sync with DB", response.get("message"));
        assertEquals(updatedContact, dbContact);
    }

    @Test
    void shouldSupportUc18RetrieveContactsByDateRange() {
        dbService.saveAddressBookWithContacts("DateBookUc18", List.of(
                new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"),
                new Contact("Aman", "Verma", "Street 2", "Bhopal", "MP", "462001", "9000000002", "aman@gmail.com")
        ));

        List<Contact> todayContacts = controller.getContactsAddedInPeriod(LocalDate.now(), LocalDate.now());
        List<Contact> oldRangeContacts = controller.getContactsAddedInPeriod(
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2000, 1, 2)
        );

        assertTrue(todayContacts.size() >= 2);
        assertEquals(0, oldRangeContacts.size());
    }

    @Test
    void shouldSupportUc19CountContactsByCityOrStateFromDb() {
        Map<String, Long> beforeCityCounts = controller.countByCityFromDatabase();
        Map<String, Long> beforeStateCounts = controller.countByStateFromDatabase();

        dbService.saveAddressBookWithContacts("CountBookUc19", List.of(
                new Contact("Rudresh", "Sharma", "Street 1", "Indore", "MP", "452001", "9000000001", "rudresh@gmail.com"),
                new Contact("Aman", "Verma", "Street 2", "Indore", "MP", "452002", "9000000002", "aman@gmail.com"),
                new Contact("Ravi", "Kumar", "Street 3", "Delhi", "Delhi", "110001", "9000000003", "ravi@gmail.com")
        ));

        Map<String, Long> cityCounts = controller.countByCityFromDatabase();
        Map<String, Long> stateCounts = controller.countByStateFromDatabase();

        assertEquals(beforeCityCounts.getOrDefault("Indore", 0L) + 2L, cityCounts.get("Indore"));
        assertEquals(beforeCityCounts.getOrDefault("Delhi", 0L) + 1L, cityCounts.get("Delhi"));
        assertEquals(beforeStateCounts.getOrDefault("MP", 0L) + 2L, stateCounts.get("MP"));
        assertEquals(beforeStateCounts.getOrDefault("Delhi", 0L) + 1L, stateCounts.get("Delhi"));
    }
}
