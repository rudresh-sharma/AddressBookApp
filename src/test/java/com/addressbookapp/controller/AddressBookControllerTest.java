package com.addressbookapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import com.addressbookapp.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressBookControllerTest {

    @Autowired
    private AddressBookController controller;

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

        assertEquals(2, byCity.size());
        assertEquals(2, byState.size());
        assertEquals(2, groupedCity.get("Delhi").size());
        assertEquals(2, groupedState.get("Delhi").size());
        assertEquals(2L, cityCount.get("Delhi"));
        assertEquals(2L, stateCount.get("Delhi"));
        assertEquals("Aman", sortedByName.get(0).getFirstName());
        assertEquals("Ravi", sortedByName.get(1).getFirstName());
        assertTrue(controller.getAddressBooks().contains("Personal"));
    }
}
