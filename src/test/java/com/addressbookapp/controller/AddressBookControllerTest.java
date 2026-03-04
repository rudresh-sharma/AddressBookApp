package com.addressbookapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void shouldAddAndEditContact() {
        Contact created = controller.addContact(new Contact(
                "Aman",
                "Verma",
                "Street 2",
                "Bhopal",
                "MP",
                "462001",
                "8888888888",
                "aman@gmail.com"
        ));

        Map<String, String> response = controller.editContact("Aman", new Contact(
                "Aman",
                "Verma",
                "New Street",
                "Indore",
                "MP",
                "452001",
                "7777777777",
                "newaman@gmail.com"
        ));

        assertNotNull(created);
        assertEquals("Contact updated successfully", response.get("message"));
        assertTrue(controller.getAllContacts().stream()
                .anyMatch(c -> "Indore".equals(c.getCity())));
    }
}
