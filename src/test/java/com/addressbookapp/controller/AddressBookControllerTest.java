package com.addressbookapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.addressbookapp.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressBookControllerTest {

    @Autowired
    private AddressBookController controller;

    @Test
    void shouldCreateAndFetchContact() {
        Contact created = controller.addContact(new Contact(
                "Aman",
                "Verma",
                "XYZ Street",
                "Bhopal",
                "MP",
                "462001",
                "8888888888",
                "aman@gmail.com"
        ));

        assertNotNull(created);
        assertEquals("Aman", created.getFirstName());
        assertTrue(controller.getAllContacts().stream()
                .anyMatch(c -> "aman@gmail.com".equals(c.getEmail())));
    }
}
