package com.addressbookapp.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.addressbookapp.model.AddressBook;
import com.addressbookapp.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class AddressBookService {

    private static final Comparator<Contact> NAME_COMPARATOR = Comparator
            .comparing(Contact::getFirstName, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(Contact::getLastName, String.CASE_INSENSITIVE_ORDER);
    private static final Comparator<Contact> CITY_COMPARATOR = Comparator
            .comparing(Contact::getCity, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(Contact::getFirstName, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(Contact::getLastName, String.CASE_INSENSITIVE_ORDER);
    private static final Comparator<Contact> STATE_COMPARATOR = Comparator
            .comparing(Contact::getState, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(Contact::getFirstName, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(Contact::getLastName, String.CASE_INSENSITIVE_ORDER);
    private static final Comparator<Contact> ZIP_COMPARATOR = Comparator
            .comparing(Contact::getZip, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(Contact::getFirstName, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(Contact::getLastName, String.CASE_INSENSITIVE_ORDER);

    private final Map<String, AddressBook> addressBookMap = new HashMap<>();

    public boolean addAddressBook(String name) {

        if (addressBookMap.containsKey(name)) {
            return false;
        }

        addressBookMap.put(name, new AddressBook());
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

    public Map<String, Long> getPersonCountByCity() {
        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .collect(Collectors.groupingBy(Contact::getCity, Collectors.counting()));
    }

    public Map<String, Long> getPersonCountByState() {
        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .collect(Collectors.groupingBy(Contact::getState, Collectors.counting()));
    }

    public List<Contact> getContactsSortedByName(String addressBookName) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null) {
            return List.of();
        }

        return addressBook.getContactList().stream()
                .sorted(NAME_COMPARATOR)
                .toList();
    }

    public List<Contact> getAllContactsSortedByName() {
        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .sorted(NAME_COMPARATOR)
                .toList();
    }

    public List<Contact> getContactsSortedByCity(String addressBookName) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null) {
            return List.of();
        }

        return addressBook.getContactList().stream()
                .sorted(CITY_COMPARATOR)
                .toList();
    }

    public List<Contact> getContactsSortedByState(String addressBookName) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null) {
            return List.of();
        }

        return addressBook.getContactList().stream()
                .sorted(STATE_COMPARATOR)
                .toList();
    }

    public List<Contact> getContactsSortedByZip(String addressBookName) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null) {
            return List.of();
        }

        return addressBook.getContactList().stream()
                .sorted(ZIP_COMPARATOR)
                .toList();
    }

    public List<Contact> getAllContactsSortedByCity() {
        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .sorted(CITY_COMPARATOR)
                .toList();
    }

    public List<Contact> getAllContactsSortedByState() {
        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .sorted(STATE_COMPARATOR)
                .toList();
    }

    public List<Contact> getAllContactsSortedByZip() {
        return addressBookMap.values().stream()
                .flatMap(addressBook -> addressBook.getContactList().stream())
                .sorted(ZIP_COMPARATOR)
                .toList();
    }
    
    
    public AddressBook getAddressBook(String name) {
        return addressBookMap.get(name);
    }

    public Set<String> getAddressBookNames() {
        return Collections.unmodifiableSet(addressBookMap.keySet());
    }

    public boolean addContact(String addressBookName, Contact contact) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        return addressBook != null && addressBook.addContact(contact);
    }

    public int addContacts(String addressBookName, List<Contact> contacts) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        return addressBook == null ? -1 : addressBook.addContacts(contacts);
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

    public boolean writeContactsToFile(String addressBookName, String filePath) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null) {
            return false;
        }

        try {
            Path path = Path.of(filePath);
            Path parent = path.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (CSVWriter writer = new CSVWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
                for (Contact contact : addressBook.getContactList()) {
                    writer.writeNext(new String[]{
                            safe(contact.getFirstName()),
                            safe(contact.getLastName()),
                            safe(contact.getAddress()),
                            safe(contact.getCity()),
                            safe(contact.getState()),
                            safe(contact.getZip()),
                            safe(contact.getPhoneNumber()),
                            safe(contact.getEmail())
                    });
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public int readContactsFromFile(String addressBookName, String filePath) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null) {
            return -1;
        }

        try (CSVReader reader = new CSVReader(Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8))) {
            int addedCount = 0;
            String[] row;
            while ((row = reader.readNext()) != null) {
                if (row.length == 0) {
                    continue;
                }
                try {
                    Contact contact = deserializeContact(row);
                    if (addressBook.addContact(contact)) {
                        addedCount++;
                    }
                } catch (IllegalArgumentException ignored) {
                    // Skip malformed lines and continue processing remaining records.
                }
            }
            return addedCount;
        } catch (IOException | CsvValidationException e) {
            return -1;
        }
    }

    private Contact deserializeContact(String[] row) {
        if (row.length < 8) {
            throw new IllegalArgumentException("Invalid contact line");
        }
        return new Contact(
                safe(row[0]),
                safe(row[1]),
                safe(row[2]),
                safe(row[3]),
                safe(row[4]),
                safe(row[5]),
                safe(row[6]),
                safe(row[7])
        );
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
