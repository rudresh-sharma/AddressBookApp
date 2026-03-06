package com.addressbookapp.repository;

import com.addressbookapp.entity.AddressBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressBookRepository extends JpaRepository<AddressBookEntity, Long> {
    Optional<AddressBookEntity> findByName(String name);
}
