package com.addressbookapp.addressbook;

import java.util.HashMap;
import java.util.Map;

public class AddressBookManager {

    private Map<String, AddressBook> addressBookMap = new HashMap<>();

    public boolean addAddressBook(String name) {

        if (addressBookMap.containsKey(name)) {
            System.out.println("Address Book with this name already exists!");
            return false;
        }

        addressBookMap.put(name, new AddressBook());
        System.out.println("Address Book added successfully!");
        return true;
    }

    public AddressBook getAddressBook(String name) {
        return addressBookMap.get(name);
    }

    public void displayAddressBooks() {
        System.out.println("Available Address Books:");
        addressBookMap.keySet().forEach(System.out::println);
    }
}