package com.sidd.bitespeed.service;

import com.sidd.bitespeed.dao.ContactRequestDTO;
import com.sidd.bitespeed.dao.ContactResponseDTO;
import com.sidd.bitespeed.model.Contact;

public interface ContactService {
    Contact identifyContact(ContactRequestDTO request);
}