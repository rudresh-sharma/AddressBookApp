package com.addressbookapp.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.addressbookapp.model.AddressBook;
import com.addressbookapp.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class AddressBookService {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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

    public boolean writeContactsToJsonFile(String addressBookName, String filePath) {
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
            String json = GSON.toJson(addressBook.getContactList());
            Files.writeString(path, json, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public int readContactsFromJsonFile(String addressBookName, String filePath) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null) {
            return -1;
        }

        try {
            String json = Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
            List<Contact> contacts = GSON.fromJson(json, new TypeToken<List<Contact>>() {}.getType());
            if (contacts == null) {
                return 0;
            }

            int addedCount = 0;
            for (Contact contact : contacts) {
                if (contact == null) {
                    continue;
                }
                if (addressBook.addContact(contact)) {
                    addedCount++;
                }
            }
            return addedCount;
        } catch (Exception e) {
            return -1;
        }
    }

    public int readContactsFromJsonServer(String addressBookName, String serverUrl) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null) {
            return -1;
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                return -1;
            }

            List<Contact> contacts = GSON.fromJson(response.body(), new TypeToken<List<Contact>>() {}.getType());
            if (contacts == null) {
                return 0;
            }

            int addedCount = 0;
            for (Contact contact : contacts) {
                if (!isValidContact(contact)) {
                    continue;
                }
                if (addressBook.addContact(contact)) {
                    addedCount++;
                }
            }
            return addedCount;
        } catch (Exception e) {
            return -1;
        }
    }

    public int addContactsToJsonServerAndSyncMemory(String addressBookName, String serverUrl) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null) {
            return -1;
        }
        List<Contact> contacts = addressBook.getContactList();
        if (contacts == null || contacts.isEmpty()) {
            return 0;
        }

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            int addedCount = 0;

            for (Contact contact : contacts) {
                if (!isValidContact(contact)) {
                    continue;
                }

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(serverUrl))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(contact), StandardCharsets.UTF_8))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
                if (!isSuccessStatus(response.statusCode())) {
                    continue;
                }

                addedCount++;
            }
            return addedCount;
        } catch (Exception e) {
            return -1;
        }
    }

    public boolean updateContactInJsonServerAndSyncMemory(String addressBookName, String firstName, String serverUrl) {
        AddressBook addressBook = addressBookMap.get(addressBookName);
        if (addressBook == null || firstName == null || firstName.isBlank()) {
            return false;
        }

        Contact memoryContact = addressBook.getContactList().stream()
                .filter(contact -> contact.getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
        if (memoryContact == null) {
            return false;
        }

        try {
            String encodedFirstName = URLEncoder.encode(firstName, StandardCharsets.UTF_8);
            String lookupUrl = serverUrl + "?firstName=" + encodedFirstName;
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest lookupRequest = HttpRequest.newBuilder()
                    .uri(URI.create(lookupUrl))
                    .GET()
                    .build();
            HttpResponse<String> lookupResponse = httpClient.send(lookupRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (!isSuccessStatus(lookupResponse.statusCode())) {
                return false;
            }

            JsonArray matches = GSON.fromJson(lookupResponse.body(), JsonArray.class);
            if (matches == null || matches.isEmpty()) {
                return false;
            }

            JsonObject matched = matches.get(0).getAsJsonObject();
            JsonElement idElement = matched.get("id");
            if (idElement == null || idElement.isJsonNull()) {
                return false;
            }

            String id = idElement.getAsString();
            String updateUrl = serverUrl.endsWith("/") ? serverUrl + id : serverUrl + "/" + id;

            HttpRequest updateRequest = HttpRequest.newBuilder()
                    .uri(URI.create(updateUrl))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(GSON.toJson(memoryContact), StandardCharsets.UTF_8))
                    .build();
            HttpResponse<String> updateResponse = httpClient.send(updateRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return isSuccessStatus(updateResponse.statusCode());
        } catch (Exception e) {
            return false;
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

    private boolean isValidContact(Contact contact) {
        return contact != null
                && contact.getFirstName() != null
                && !contact.getFirstName().isBlank()
                && contact.getLastName() != null
                && !contact.getLastName().isBlank();
    }

    private boolean isSuccessStatus(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }
}
