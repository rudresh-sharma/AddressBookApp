package com.addressbookapp.repository;

import com.addressbookapp.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    Optional<ContactEntity> findFirstByAddressBook_NameIgnoreCaseAndFirstNameIgnoreCase(String addressBookName, String firstName);
    List<ContactEntity> findByDateAddedBetween(LocalDateTime start, LocalDateTime end);
}
