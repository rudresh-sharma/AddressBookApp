package com.addressbookapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.addressbookapp.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressBookControllerTest {

    @Autowired
    private AddressBookController controller;

    @Test
    void shouldSupportAddressBooksAndContactCrud() {
        Map<String, String> addBook = controller.addAddressBook("Personal");
        Map<String, Object> addOne = controller.addContact("Personal", new Contact(
                "Aman", "Verma", "Street", "Bhopal", "MP", "462001", "8888888888", "aman@gmail.com"
        ));
        Map<String, Object> addBulk = controller.addContacts("Personal", List.of(
                new Contact("Ravi", "Kumar", "Addr1", "Delhi", "Delhi", "110001", "9999999999", "ravi@gmail.com"),
                new Contact("Neha", "Sharma", "Addr2", "Pune", "MH", "411001", "6666666666", "neha@gmail.com")
        ));
        Map<String, String> edit = controller.editContact("Personal", "Aman", new Contact(
                "Aman", "Verma", "New Street", "Indore", "MP", "452001", "7777777777", "newaman@gmail.com"
        ));
        Map<String, String> delete = controller.deleteContact("Personal", "Ravi");
        Set<String> books = controller.getAddressBooks();
        List<Contact> contacts = controller.getContacts("Personal");

        assertEquals("Address Book added successfully", addBook.get("message"));
        assertEquals("Contact added successfully", addOne.get("message"));
        assertEquals("Contacts added successfully", addBulk.get("message"));
        assertEquals("Contact updated successfully", edit.get("message"));
        assertEquals("Contact deleted successfully", delete.get("message"));
        assertTrue(books.contains("Personal"));
        assertEquals(2, contacts.size());
    }
}
