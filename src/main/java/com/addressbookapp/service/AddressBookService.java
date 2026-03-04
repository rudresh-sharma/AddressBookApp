package com.addressbookapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.addressbookapp.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class AddressBookService {

    private final List<Contact> contactList = new ArrayList<>();
    
    
    public List<Contact> getContactList() {
        return Collections.unmodifiableList(contactList);
    }
    
    
    
    public Contact addContact(Contact contact) {
        contactList.add(contact);
        return contact;
    }

    public List<Contact> addContacts(List<Contact> contacts) {
        contactList.addAll(contacts);
        return contacts;
    }
    
    public boolean editContact(String firstName, Contact updatedContact) {

        for (Contact contact : contactList) {

            if (contact.getFirstName().equalsIgnoreCase(firstName)) {
                contact.setAddress(updatedContact.getAddress());
                contact.setCity(updatedContact.getCity());
                contact.setState(updatedContact.getState());
                contact.setZip(updatedContact.getZip());
                contact.setPhoneNumber(updatedContact.getPhoneNumber());
                contact.setEmail(updatedContact.getEmail());
                return true;
            }
        }
        return false;
    }
    
    
    
    public boolean deleteContact(String firstName) {

        return contactList.removeIf(contact ->
                contact.getFirstName().equalsIgnoreCase(firstName));
    }
}
