package com.addressbookapp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "address_books")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "contacts")
public class AddressBookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactEntity> contacts = new ArrayList<>();

    public AddressBookEntity(String name) {
        this.name = name;
    }

    public void addContact(ContactEntity contact) {
        contacts.add(contact);
        contact.setAddressBook(this);
    }
}
