package com.addressbookapp.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.time.LocalDate;

import com.addressbookapp.model.Contact;
import com.addressbookapp.service.AddressBookDbService;
import com.addressbookapp.service.AddressBookService;
import org.springframework.http.HttpStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address-books")
public class AddressBookController {

    private final AddressBookService addressBookService;
    private final AddressBookDbService addressBookDbService;

    public AddressBookController(AddressBookService addressBookService, AddressBookDbService addressBookDbService) {
        this.addressBookService = addressBookService;
        this.addressBookDbService = addressBookDbService;
    }

    @PostMapping("/{name}")
    public Map<String, String> addAddressBook(@PathVariable String name) {
        boolean added = addressBookService.addAddressBook(name);
        return added
                ? Map.of("message", "Address Book added successfully")
                : Map.of("message", "Address Book with this name already exists");
    }

    @GetMapping
    public Set<String> getAddressBooks() {
        return addressBookService.getAddressBookNames();
    }

    @GetMapping("/contacts/search")
    public List<Contact> searchContacts(@RequestParam(required = false) String city,
                                        @RequestParam(required = false) String state) {
        if (city != null && !city.isBlank()) {
            return addressBookService.searchByCity(city);
        }
        if (state != null && !state.isBlank()) {
            return addressBookService.searchByState(state);
        }
        return List.of();
    }

    @GetMapping("/contacts/group/city")
    public Map<String, List<Contact>> viewByCity() {
        return addressBookService.getPersonsGroupedByCity();
    }

    @GetMapping("/contacts/group/state")
    public Map<String, List<Contact>> viewByState() {
        return addressBookService.getPersonsGroupedByState();
    }

    @GetMapping("/contacts/count/city")
    public Map<String, Long> countByCity() {
        return addressBookService.getPersonCountByCity();
    }

    @GetMapping("/contacts/count/state")
    public Map<String, Long> countByState() {
        return addressBookService.getPersonCountByState();
    }

    @GetMapping("/contacts/sort/name")
    public List<Contact> getAllContactsSortedByName() {
        return addressBookService.getAllContactsSortedByName();
    }

    @GetMapping("/contacts/sort/city")
    public List<Contact> getAllContactsSortedByCity() {
        return addressBookService.getAllContactsSortedByCity();
    }

    @GetMapping("/contacts/sort/state")
    public List<Contact> getAllContactsSortedByState() {
        return addressBookService.getAllContactsSortedByState();
    }

    @GetMapping("/contacts/sort/zip")
    public List<Contact> getAllContactsSortedByZip() {
        return addressBookService.getAllContactsSortedByZip();
    }

    @GetMapping("/db/contacts")
    public List<Contact> getAllEntriesFromDatabase() {
        return addressBookDbService.retrieveAllEntriesFromDb();
    }

    @GetMapping("/db/contacts/count/city")
    public Map<String, Long> countByCityFromDatabase() {
        return addressBookDbService.getPersonCountByCityFromDb();
    }

    @GetMapping("/db/contacts/count/state")
    public Map<String, Long> countByStateFromDatabase() {
        return addressBookDbService.getPersonCountByStateFromDb();
    }

    @GetMapping("/db/contacts/by-date")
    public List<Contact> getContactsAddedInPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        return addressBookDbService.retrieveContactsAddedBetween(fromDate, toDate);
    }

    @GetMapping("/{name}/db/contacts/{firstName}")
    public Contact getContactFromDatabase(@PathVariable String name,
                                          @PathVariable String firstName) {
        return addressBookDbService.getContactFromDb(name, firstName);
    }

    @GetMapping("/{name}/contacts")
    public List<Contact> getContacts(@PathVariable String name) {
        List<Contact> contacts = addressBookService.getContacts(name);
        return contacts == null ? List.of() : contacts;
    }

    @GetMapping("/{name}/contacts/sort/name")
    public List<Contact> getContactsSortedByName(@PathVariable String name) {
        return addressBookService.getContactsSortedByName(name);
    }

    @GetMapping("/{name}/contacts/sort/city")
    public List<Contact> getContactsSortedByCity(@PathVariable String name) {
        return addressBookService.getContactsSortedByCity(name);
    }

    @GetMapping("/{name}/contacts/sort/state")
    public List<Contact> getContactsSortedByState(@PathVariable String name) {
        return addressBookService.getContactsSortedByState(name);
    }

    @GetMapping("/{name}/contacts/sort/zip")
    public List<Contact> getContactsSortedByZip(@PathVariable String name) {
        return addressBookService.getContactsSortedByZip(name);
    }

    @PostMapping("/{name}/contacts/file/write")
    public Map<String, String> writeContactsToFile(@PathVariable String name,
                                                   @RequestParam String filePath) {
        boolean written = addressBookService.writeContactsToFile(name, filePath);
        return written
                ? Map.of("message", "Contacts written to file successfully")
                : Map.of("message", "Address Book not found or file write failed");
    }

    @PostMapping("/{name}/contacts/file/read")
    public Map<String, String> readContactsFromFile(@PathVariable String name,
                                                    @RequestParam String filePath) {
        int addedCount = addressBookService.readContactsFromFile(name, filePath);
        return addedCount >= 0
                ? Map.of("message", "Contacts read from file successfully", "addedCount", String.valueOf(addedCount))
                : Map.of("message", "Address Book not found or file read failed");
    }

    @PostMapping("/{name}/contacts/json/write")
    public Map<String, String> writeContactsToJsonFile(@PathVariable String name,
                                                       @RequestParam String filePath) {
        boolean written = addressBookService.writeContactsToJsonFile(name, filePath);
        return written
                ? Map.of("message", "Contacts written to JSON file successfully")
                : Map.of("message", "Address Book not found or JSON file write failed");
    }

    @PostMapping("/{name}/contacts/json/read")
    public Map<String, String> readContactsFromJsonFile(@PathVariable String name,
                                                        @RequestParam String filePath) {
        int addedCount = addressBookService.readContactsFromJsonFile(name, filePath);
        return addedCount >= 0
                ? Map.of("message", "Contacts read from JSON file successfully", "addedCount", String.valueOf(addedCount))
                : Map.of("message", "Address Book not found or JSON file read failed");
    }

    @PostMapping("/{name}/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> addContact(@PathVariable String name, @RequestBody Contact contact) {
        boolean added = addressBookService.addContact(name, contact);
        return added
                ? Map.of("message", "Contact added successfully")
                : Map.of("message", "Duplicate contact found or Address Book not found");
    }

    @PostMapping("/{name}/db/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> addContactToDatabase(@PathVariable String name, @RequestBody Contact contact) {
        boolean added = addressBookDbService.addContactToAddressBookDb(name, contact);
        return added
                ? Map.of("message", "Contact added to database successfully")
                : Map.of("message", "Duplicate contact found in database");
    }

    @PostMapping("/{name}/db/contacts/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> addContactsToDatabase(@PathVariable String name, @RequestBody List<Contact> contacts) {
        int addedCount = addressBookDbService.addContactsToAddressBookDbUsingThreads(name, contacts);
        return Map.of("message", "Contacts added to database using threads", "addedCount", String.valueOf(addedCount));
    }

    @PostMapping("/{name}/contacts/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> addContacts(@PathVariable String name, @RequestBody List<Contact> contacts) {
        int addedCount = addressBookService.addContacts(name, contacts);
        return addedCount < 0
                ? Map.of("message", "Address Book not found")
                : Map.of("message", "Contacts processed", "addedCount", String.valueOf(addedCount));
    }

    @PutMapping("/{name}/contacts/{firstName}")
    public Map<String, String> editContact(@PathVariable String name,
                                           @PathVariable String firstName,
                                           @RequestBody Contact updatedContact) {
        boolean updated = addressBookService.editContact(name, firstName, updatedContact);
        return updated
                ? Map.of("message", "Contact updated successfully")
                : Map.of("message", "Contact or Address Book not found");
    }

    @PutMapping("/{name}/contacts/{firstName}/sync-db")
    public Map<String, String> updateContactAndSyncWithDb(@PathVariable String name,
                                                          @PathVariable String firstName,
                                                          @RequestBody Contact updatedContact) {
        boolean memoryUpdated = addressBookService.editContact(name, firstName, updatedContact);
        boolean dbUpdated = addressBookDbService.updateContactInDb(name, firstName, updatedContact);

        if (!memoryUpdated || !dbUpdated) {
            return Map.of("message", "Contact not found in memory or DB");
        }

        Contact memoryContact = addressBookService.getContacts(name).stream()
                .filter(contact -> contact.getFirstName().equalsIgnoreCase(firstName))
                .findFirst()
                .orElse(null);
        boolean inSync = memoryContact != null && addressBookDbService.isMemoryInSyncWithDb(name, firstName, memoryContact);

        return inSync
                ? Map.of("message", "Contact updated and memory is in sync with DB")
                : Map.of("message", "Contact updated but memory is not in sync with DB");
    }

    @DeleteMapping("/{name}/contacts/{firstName}")
    public Map<String, String> deleteContact(@PathVariable String name,
                                             @PathVariable String firstName) {
        boolean deleted = addressBookService.deleteContact(name, firstName);
        return deleted
                ? Map.of("message", "Contact deleted successfully")
                : Map.of("message", "Contact or Address Book not found");
    }
}
