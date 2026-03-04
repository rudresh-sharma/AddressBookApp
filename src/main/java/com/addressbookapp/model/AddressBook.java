package com.addressbookapp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddressBook {

    private final List<Contact> contactList = new ArrayList<>();
    
    
    public List<Contact> getContactList() {
        return Collections.unmodifiableList(contactList);
    }
    
    
    
    public boolean addContact(Contact newContact) {

        boolean exists = contactList.stream()
                .anyMatch(contact -> contact.equals(newContact));

        if (exists) {
            System.out.println("Duplicate contact found. Cannot add!");
            return false;
        }

        contactList.add(newContact);
        return true;
    }

    public int addContacts(List<Contact> contacts) {
        int addedCount = 0;
        for (Contact contact : contacts) {
            if (addContact(contact)) {
                addedCount++;
            }
        }
        return addedCount;
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
