package com.addressbookapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	    return this.firstName.equalsIgnoreCase(contact.firstName) &&
	           this.lastName.equalsIgnoreCase(contact.lastName);
	}

	@Override
	public int hashCode() {
	    return (firstName.toLowerCase() + lastName.toLowerCase()).hashCode();
	}
}
