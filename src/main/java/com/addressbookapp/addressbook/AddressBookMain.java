package com.addressbookapp.addressbook;

import java.util.Scanner;

public class AddressBookMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        AddressBookManager manager = new AddressBookManager();

        while (true) {

            System.out.println("\n1. Add Address Book");
            System.out.println("2. Show Address Books");
            System.out.println("3. Exit");

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

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}