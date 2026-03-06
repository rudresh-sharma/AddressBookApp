package com.addressbookapp.service;

import com.addressbookapp.entity.AddressBookEntity;
import com.addressbookapp.entity.ContactEntity;
import com.addressbookapp.model.Contact;
import com.addressbookapp.repository.AddressBookRepository;
import com.addressbookapp.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
