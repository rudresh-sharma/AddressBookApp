package com.addressbookapp.service;

import com.addressbookapp.entity.AddressBookEntity;
import com.addressbookapp.entity.ContactEntity;
import com.addressbookapp.model.Contact;
import com.addressbookapp.repository.AddressBookRepository;
import com.addressbookapp.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AddressBookDbService {

    private final AddressBookRepository addressBookRepository;
    private final ContactRepository contactRepository;

    public AddressBookDbService(AddressBookRepository addressBookRepository, ContactRepository contactRepository) {
        this.addressBookRepository = addressBookRepository;
        this.contactRepository = contactRepository;
    }

    @Transactional(readOnly = true)
    public List<Contact> retrieveAllEntriesFromDb() {
        return contactRepository.findAll().stream()
                .map(this::toModel)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Contact> retrieveContactsAddedBetween(LocalDate fromDate, LocalDate toDate) {
        LocalDateTime startDateTime = fromDate.atStartOfDay();
        LocalDateTime endDateTime = toDate.plusDays(1).atStartOfDay().minusNanos(1);
        return contactRepository.findByDateAddedBetween(startDateTime, endDateTime).stream()
                .map(this::toModel)
                .toList();
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getPersonCountByCityFromDb() {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Object[] row : contactRepository.countContactsByCity()) {
            String city = (String) row[0];
            Long count = (Long) row[1];
            result.put(city, count);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getPersonCountByStateFromDb() {
        Map<String, Long> result = new LinkedHashMap<>();
        for (Object[] row : contactRepository.countContactsByState()) {
            String state = (String) row[0];
            Long count = (Long) row[1];
            result.put(state, count);
        }
        return result;
    }

    @Transactional
    public AddressBookEntity saveAddressBookWithContacts(String name, List<Contact> contacts) {
        AddressBookEntity addressBook = new AddressBookEntity(name);
        for (Contact contact : contacts) {
            ContactEntity entity = new ContactEntity(
                    contact.getFirstName(),
                    contact.getLastName(),
                    contact.getAddress(),
                    contact.getCity(),
                    contact.getState(),
                    contact.getZip(),
                    contact.getPhoneNumber(),
                    contact.getEmail()
            );
            addressBook.addContact(entity);
        }
        return addressBookRepository.save(addressBook);
    }

    @Transactional
    public boolean addContactToAddressBookDb(String addressBookName, Contact contact) {
        Optional<ContactEntity> existingContact =
                contactRepository.findFirstByAddressBook_NameIgnoreCaseAndFirstNameIgnoreCaseAndLastNameIgnoreCase(
                        addressBookName, contact.getFirstName(), contact.getLastName());
        if (existingContact.isPresent()) {
            return false;
        }

        AddressBookEntity addressBook = addressBookRepository.findByName(addressBookName)
                .orElseGet(() -> addressBookRepository.save(new AddressBookEntity(addressBookName)));

        ContactEntity contactEntity = new ContactEntity(
                contact.getFirstName(),
                contact.getLastName(),
                contact.getAddress(),
                contact.getCity(),
                contact.getState(),
                contact.getZip(),
                contact.getPhoneNumber(),
                contact.getEmail()
        );
        addressBook.addContact(contactEntity);
        addressBookRepository.save(addressBook);
        return true;
    }

    @Transactional(readOnly = true)
    public Contact getContactFromDb(String addressBookName, String firstName) {
        return contactRepository.findFirstByAddressBook_NameIgnoreCaseAndFirstNameIgnoreCase(addressBookName, firstName)
                .map(this::toModel)
                .orElse(null);
    }

    @Transactional
    public boolean updateContactInDb(String addressBookName, String firstName, Contact updatedContact) {
        Optional<ContactEntity> contactOptional =
                contactRepository.findFirstByAddressBook_NameIgnoreCaseAndFirstNameIgnoreCase(addressBookName, firstName);

        if (contactOptional.isEmpty()) {
            return false;
        }

        ContactEntity contact = contactOptional.get();
        contact.setAddress(updatedContact.getAddress());
        contact.setCity(updatedContact.getCity());
        contact.setState(updatedContact.getState());
        contact.setZip(updatedContact.getZip());
        contact.setPhoneNumber(updatedContact.getPhoneNumber());
        contact.setEmail(updatedContact.getEmail());
        contactRepository.save(contact);
        return true;
    }

    @Transactional(readOnly = true)
    public boolean isMemoryInSyncWithDb(String addressBookName, String firstName, Contact memoryContact) {
        Contact dbContact = getContactFromDb(addressBookName, firstName);
        return dbContact != null && dbContact.equals(memoryContact);
    }

    private Contact toModel(ContactEntity entity) {
        return new Contact(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getState(),
                entity.getZip(),
                entity.getPhoneNumber(),
                entity.getEmail()
        );
    }
}
