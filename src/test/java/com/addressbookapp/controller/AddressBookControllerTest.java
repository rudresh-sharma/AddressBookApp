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
    void shouldSupportUc1ToUc5Flows() {
        controller.addContact(new Contact(
                "Aman", "Verma", "Street 2",
                "Bhopal", "MP", "462001",
                "8888888888", "aman@gmail.com"
        ));

        Map<String, String> editResponse = controller.editContact("Aman", new Contact(
                "Aman", "Verma", "New Street",
                "Indore", "MP", "452001",
                "7777777777", "newaman@gmail.com"
        ));

        List<Contact> bulkAdded = controller.addContacts(List.of(
                new Contact("Ravi", "Kumar", "Addr1", "Delhi", "Delhi", "110001", "9999999999", "ravi@gmail.com"),
                new Contact("Neha", "Sharma", "Addr2", "Pune", "MH", "411001", "6666666666", "neha@gmail.com")
        ));

        Map<String, String> deleteResponse = controller.deleteContact("Ravi");

        assertEquals("Contact updated successfully", editResponse.get("message"));
        assertEquals("Contact deleted successfully", deleteResponse.get("message"));
        assertEquals(2, bulkAdded.size());
        assertTrue(controller.getAllContacts().stream()
                .anyMatch(c -> "Neha".equals(c.getFirstName())));
    }
}
