package com.addressbookapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void shouldRejectDuplicateContactInSameAddressBook() {
        controller.addAddressBook("Personal");

        Map<String, String> first = controller.addContact("Personal", new Contact(
                "Ravi", "Kumar", "Addr1", "Delhi", "Delhi", "110001", "9999999999", "ravi@gmail.com"
        ));
        Map<String, String> duplicate = controller.addContact("Personal", new Contact(
                "Ravi", "Kumar", "Addr2", "Pune", "MH", "411001", "8888888888", "ravi2@gmail.com"
        ));

        assertEquals("Contact added successfully", first.get("message"));
        assertEquals("Duplicate contact found or Address Book not found", duplicate.get("message"));
        assertEquals(1, controller.getContacts("Personal").size());
        assertTrue(controller.getAddressBooks().contains("Personal"));
    }
}
