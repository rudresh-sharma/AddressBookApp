package com.addressbookapp.repository;

import com.addressbookapp.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    Optional<ContactEntity> findFirstByAddressBook_NameIgnoreCaseAndFirstNameIgnoreCase(String addressBookName, String firstName);
    List<ContactEntity> findByDateAddedBetween(LocalDateTime start, LocalDateTime end);

    @Query("select c.city, count(c) from ContactEntity c group by c.city")
    List<Object[]> countContactsByCity();

    @Query("select c.state, count(c) from ContactEntity c group by c.state")
    List<Object[]> countContactsByState();
}
