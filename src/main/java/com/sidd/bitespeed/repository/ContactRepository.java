package com.sidd.bitespeed.repository;

import com.sidd.bitespeed.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findByEmail(String email);
    Contact findByPhoneNumber(String phoneNumber);

}