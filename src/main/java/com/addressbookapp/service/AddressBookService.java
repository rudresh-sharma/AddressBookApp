package com.addressbookapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.addressbookapp.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class AddressBookService {

    private final List<Contact> contactList = new ArrayList<>();

    public Contact addContact(Contact contact) {
        contactList.add(contact);
        return contact;
    }
    
    public List<Contact> getContactList() {
        return Collections.unmodifiableList(contactList);
    }
}
