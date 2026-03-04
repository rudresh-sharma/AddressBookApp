package com.addressbookapp.addressbook;

import java.util.Scanner;
import java.util.List;
public class AddressBookMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AddressBookManager manager = new AddressBookManager();

        while (true) {

            System.out.println("\n1. Add Address Book");
            System.out.println("2. Show Address Books");
            System.out.println("3. Exit");
            System.out.println(  "4. Search By City ");
            System.out.println(  "5. Search by State ");

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    System.out.print("Enter Address Book Name: ");
                    String name = scanner.nextLine();
                    manager.addAddressBook(name);
                    break;

                case 2:
                    manager.displayAddressBooks();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return;
                    
                    
                case 4:
                    System.out.print("Enter City to search: ");
                    String city = scanner.nextLine();
                    List<Contact> cityResults = manager.searchByCity(city);

                    cityResults.forEach(System.out::println);
                    break;

                case 5:
                    System.out.print("Enter State to search: ");
                    String state = scanner.nextLine();
                    List<Contact> stateResults = manager.searchByState(state);

                    stateResults.forEach(System.out::println);
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}