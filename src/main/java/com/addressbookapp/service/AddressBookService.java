package com.addressbookapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.addressbookapp.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class AddressBookService {

    private final List<Contact> contacts = new ArrayList<>();

    public Contact addContact(Contact contact) {
        contacts.add(contact);
        return contact;
    }

    public List<Contact> getAllContacts() {
        return Collections.unmodifiableList(contacts);
    }
}
