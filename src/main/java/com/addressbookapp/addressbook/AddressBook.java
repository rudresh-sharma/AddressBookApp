package com.addressbookapp.addressbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {

    private List<Contact> contactList = new ArrayList<>();
    
    
    public List<Contact> getContactList() {
        return contactList;
    }
    
    
    
    public boolean addContact(Contact newContact) {

        boolean exists = contactList.stream()
                .anyMatch(contact -> contact.equals(newContact));

        if (exists) {
            System.out.println("Duplicate contact found. Cannot add!");
            return false;
        }

        contactList.add(newContact);
        System.out.println("Contact added successfully!");
        return true;
    }

    public void displayContacts() {
        if (contactList.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            contactList.forEach(System.out::println);
        }
    }
    
    public void editContact(String firstName, Scanner scanner) {

        for (Contact contact : contactList) {

            if (contact.getFirstName().equalsIgnoreCase(firstName)) {

                System.out.print("Enter new Address: ");
                contact.setAddress(scanner.nextLine());

                System.out.print("Enter new City: ");
                contact.setCity(scanner.nextLine());

                System.out.print("Enter new State: ");
                contact.setState(scanner.nextLine());

                System.out.print("Enter new Zip: ");
                contact.setZip(scanner.nextLine());

                System.out.print("Enter new Phone Number: ");
                contact.setPhoneNumber(scanner.nextLine());

                System.out.print("Enter new Email: ");
                contact.setEmail(scanner.nextLine());

                System.out.println("Contact updated successfully!");
                return;
            }
        }

        System.out.println("Contact not found.");
    }
    
    
    
    public boolean deleteContact(String firstName) {

        return contactList.removeIf(contact ->
                contact.getFirstName().equalsIgnoreCase(firstName));
    }
}