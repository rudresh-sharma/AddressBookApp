package com.addressbookapp.addressbook;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AddressBookManager manager = new AddressBookManager();

        while (true) {

            System.out.println("\n===== ADDRESS BOOK SYSTEM =====");
            System.out.println("1. Add Address Book");
            System.out.println("2. Add Contact");
            System.out.println("3. Search Person by City");
            System.out.println("4. Search Person by State");
            System.out.println("5. View Persons Grouped by City");
            System.out.println("6. View Persons Grouped by State");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                // ==============================
                // UC6 - Add Address Book
                // ==============================
                case 1:
                    System.out.print("Enter Address Book Name: ");
                    String bookName = scanner.nextLine();
                    manager.addAddressBook(bookName);
                    break;

                // ==============================
                // UC5 + UC7 - Add Contact (No Duplicate)
                // ==============================
                case 2:

                    System.out.print("Enter Address Book Name: ");
                    String selectedBook = scanner.nextLine();

                    AddressBook addressBook =
                            manager.getAddressBook(selectedBook);

                    if (addressBook == null) {
                        System.out.println("Address Book not found!");
                        break;
                    }

                    String continueChoice;

                    do {

                        System.out.print("Enter First Name: ");
                        String firstName = scanner.nextLine();

                        System.out.print("Enter Last Name: ");
                        String lastName = scanner.nextLine();

                        System.out.print("Enter Address: ");
                        String address = scanner.nextLine();

                        System.out.print("Enter City: ");
                        String city = scanner.nextLine();

                        System.out.print("Enter State: ");
                        String state = scanner.nextLine();

                        System.out.print("Enter Zip: ");
                        String zip = scanner.nextLine();

                        System.out.print("Enter Phone Number: ");
                        String phone = scanner.nextLine();

                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();

                        Contact contact = new Contact(
                                firstName, lastName, address,
                                city, state, zip, phone, email
                        );

                        addressBook.addContact(contact);

                        System.out.print("Add another contact? (yes/no): ");
                        continueChoice = scanner.nextLine();

                    } while (continueChoice.equalsIgnoreCase("yes"));

                    break;

                // ==============================
                // UC8 - Search by City
                // ==============================
                case 3:

                    System.out.print("Enter City: ");
                    String searchCity = scanner.nextLine();

                    List<Contact> cityResults =
                            manager.searchByCity(searchCity);

                    if (cityResults.isEmpty()) {
                        System.out.println("No contacts found.");
                    } else {
                        cityResults.forEach(System.out::println);
                    }

                    break;

                // ==============================
                // UC8 - Search by State
                // ==============================
                case 4:

                    System.out.print("Enter State: ");
                    String searchState = scanner.nextLine();

                    List<Contact> stateResults =
                            manager.searchByState(searchState);

                    if (stateResults.isEmpty()) {
                        System.out.println("No contacts found.");
                    } else {
                        stateResults.forEach(System.out::println);
                    }

                    break;

                // ==============================
                // UC9 - View Grouped by City
                // ==============================
                case 5:

                    Map<String, List<Contact>> cityMap =
                            manager.getPersonsGroupedByCity();

                    cityMap.forEach((city, contacts) -> {
                        System.out.println("\nCity: " + city);
                        contacts.forEach(System.out::println);
                    });

                    break;

                // ==============================
                // UC9 - View Grouped by State
                // ==============================
                case 6:

                    Map<String, List<Contact>> stateMap =
                            manager.getPersonsGroupedByState();

                    stateMap.forEach((state, contacts) -> {
                        System.out.println("\nState: " + state);
                        contacts.forEach(System.out::println);
                    });

                    break;

                case 7:
                    System.out.println("Exiting Application...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}