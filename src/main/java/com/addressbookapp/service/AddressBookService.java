package com.addressbookapp.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.addressbookapp.model.AddressBook;
import com.addressbookapp.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class AddressBookService {

    private final Map<String, AddressBook> addressBookMap = new HashMap<>();

    public boolean addAddressBook(String name) {

        if (addressBookMap.containsKey(name)) {
            return false;
        }

        addressBookMap.put(name, new AddressBook());
        return true;
    }

    public AddressBook getAddressBook(String name) {
        return addressBookMap.get(name);
    }

    public Set<String> getAddressBookNames() {
        return Collections.unmodifiableSet(addressBookMap.keySet());
    }

    public Contact addContact(String addressBookName, Contact contact) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        return addressBook == null ? null : addressBook.addContact(contact);
    }

    public List<Contact> addContacts(String addressBookName, List<Contact> contacts) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        return addressBook == null ? null : addressBook.addContacts(contacts);
    }

    public List<Contact> getContacts(String addressBookName) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        return addressBook == null ? null : addressBook.getContactList();
    }

    public boolean editContact(String addressBookName, String firstName, Contact updatedContact) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        return addressBook != null && addressBook.editContact(firstName, updatedContact);
    }

    public boolean deleteContact(String addressBookName, String firstName) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        return addressBook != null && addressBook.deleteContact(firstName);
    }
}
