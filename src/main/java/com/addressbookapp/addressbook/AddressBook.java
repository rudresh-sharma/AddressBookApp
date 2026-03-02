package com.addressbookapp.addressbook;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {

    private List<Contact> contacts = new ArrayList<>();

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void displayContacts() {
        contacts.forEach(System.out::println);
    }
}