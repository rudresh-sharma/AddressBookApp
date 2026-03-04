package com.addressbookapp.addressbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Contact> searchByCity(String city) {

        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .filter(contact -> contact.getCity().equalsIgnoreCase(city))
                .toList();
    }
    
    public List<Contact> searchByState(String state) {

        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .filter(contact -> contact.getState().equalsIgnoreCase(state))
                .toList();
    }
    
    public Map<String, List<Contact>> getPersonsGroupedByCity() {

        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .collect(Collectors.groupingBy(Contact::getCity));
    }
    
    public Map<String, List<Contact>> getPersonsGroupedByState() {

        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .collect(Collectors.groupingBy(Contact::getState));
    }
    
    
    public AddressBook getAddressBook(String name) {
        return addressBookMap.get(name);
    }

    public void displayAddressBooks() {
        System.out.println("Available Address Books:");
        addressBookMap.keySet().forEach(System.out::println);
    }
}