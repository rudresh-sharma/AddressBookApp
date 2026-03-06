package com.addressbookapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;
    private String email;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Contact contact = (Contact) obj;
        return firstName.equalsIgnoreCase(contact.firstName)
                && lastName.equalsIgnoreCase(contact.lastName);
    }

    @Override
    public int hashCode() {
        return (firstName.toLowerCase() + lastName.toLowerCase()).hashCode();
    }
}
