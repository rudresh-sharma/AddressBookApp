package com.addressbookapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
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
    public String toString() {
        return firstName + " " + lastName + ", " +
                address + ", " + city + ", " + state +
                " - " + zip + ", Phone: " + phoneNumber +
                ", Email: " + email;
    }
	
	@Override
	public boolean equals(Object obj) {

	    if (this == obj) return true;

	    if (obj == null || getClass() != obj.getClass()) return false;

	    Contact contact = (Contact) obj;

	    return equalsIgnoreCase(firstName, contact.firstName) &&
               equalsIgnoreCase(lastName, contact.lastName) &&
               equalsIgnoreCase(address, contact.address) &&
               equalsIgnoreCase(city, contact.city) &&
               equalsIgnoreCase(state, contact.state) &&
               equalsIgnoreCase(zip, contact.zip) &&
               equalsIgnoreCase(phoneNumber, contact.phoneNumber) &&
               equalsIgnoreCase(email, contact.email);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(
                normalize(firstName),
                normalize(lastName),
                normalize(address),
                normalize(city),
                normalize(state),
                normalize(zip),
                normalize(phoneNumber),
                normalize(email)
        );
	}

    private static boolean equalsIgnoreCase(String a, String b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equalsIgnoreCase(b);
    }

    private static String normalize(String value) {
        return value == null ? "" : value.toLowerCase();
    }
}
