package com.addressbookapp.controller;

import java.util.List;
import java.util.Map;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contacts")
public class AddressBookController {

    private final AddressBookService addressBookService;

    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    @GetMapping
    public List<Contact> getAllContacts() {
        return addressBookService.getContactList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contact addContact(@RequestBody Contact contact) {
        return addressBookService.addContact(contact);
    }

    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Contact> addContacts(@RequestBody List<Contact> contacts) {
        return addressBookService.addContacts(contacts);
    }

    @PutMapping("/{firstName}")
    public Map<String, String> editContact(@PathVariable String firstName,
                                           @RequestBody Contact updatedContact) {
        boolean updated = addressBookService.editContact(firstName, updatedContact);
        return updated
                ? Map.of("message", "Contact updated successfully")
                : Map.of("message", "Contact not found");
    }

    @DeleteMapping("/{firstName}")
    public Map<String, String> deleteContact(@PathVariable String firstName) {
        boolean deleted = addressBookService.deleteContact(firstName);
        return deleted
                ? Map.of("message", "Contact deleted successfully")
                : Map.of("message", "Contact not found");
    }
}
