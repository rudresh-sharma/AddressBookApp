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
    void shouldSupportUc8SearchByCityAndState() {
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
        Map<String, String> duplicate = controller.addContact("Personal", new Contact(
                "Ravi", "Kumar", "Addr3", "Mumbai", "MH", "400001", "7777777777", "dup@gmail.com"
        ));

        assertEquals(2, byCity.size());
        assertEquals(2, byState.size());
        assertEquals("Duplicate contact found or Address Book not found", duplicate.get("message"));
        assertTrue(controller.getAddressBooks().contains("Personal"));
    }
}
