package com.sidd.bitespeed.service;

import com.sidd.bitespeed.constant.LinkPrecedenceEnum;
import com.sidd.bitespeed.dao.ContactRequestDTO;
import com.sidd.bitespeed.model.Contact;
import com.sidd.bitespeed.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact identifyContact(ContactRequestDTO requestDTO) {
        // Get the email and phoneNumber from the requestDTO
        String email = requestDTO.getEmail();
        String phoneNumber = requestDTO.getPhoneNumber();

        int emailCount = 0;
        int phoneCount = 0;
        List<Contact> allContacts = contactRepository.findAll();

        Contact emailContact = null;
        Contact phoneContact = null;

        Contact existingContactWithEmail = null;
        Contact existingContactWithPhoneNumber = null;

        for (Contact con : allContacts) {
            if (con.getEmail() != null && con.getEmail().equals(email)) {
                emailCount++;
                if (con.getLinkPrecedence() == LinkPrecedenceEnum.PRIMARY)
                    emailContact = con;
            }
            if (con.getPhoneNumber() != null && con.getPhoneNumber().equals(phoneNumber)) {
                phoneCount++;
                if (con.getLinkPrecedence() == LinkPrecedenceEnum.PRIMARY)
                    phoneContact = con;
            }
        }

        // If request has email and phoneNumber from two different existing contacts those are Primary
        if (emailContact != null && phoneContact != null) {
            Contact contact = new Contact();
            contact.setId(emailContact.getId());
            contact.getEmails().add(emailContact.getEmail());
            contact.getEmails().add(phoneContact.getEmail());
            contact.getPhoneNumbers().add(emailContact.getPhoneNumber());
            contact.getPhoneNumbers().add(phoneContact.getPhoneNumber());
            contact.getSecondaryContactIds().add(phoneContact.getId());

            for (Contact cont : allContacts) {
                if (emailContact.getId() == cont.getLinkedId()) {
                    if (cont.getPhoneNumber() != "" && cont.getPhoneNumber() != null && cont.getPhoneNumber().length() != 0)
                        contact.getPhoneNumbers().add(cont.getPhoneNumber());
                    if (cont.getEmail() != "" && cont.getEmail() != null && cont.getEmail().length() != 0)
                        contact.getEmails().add(cont.getEmail());
                    contact.getSecondaryContactIds().add(cont.getId());
                }
            }

            phoneContact.setLinkedId(emailContact.getId());
            phoneContact.setLinkPrecedence(LinkPrecedenceEnum.SECONDARY);
            phoneContact.setCreatedAt(LocalDateTime.now());
            phoneContact.setUpdatedAt(LocalDateTime.now());
            contactRepository.save(phoneContact);

            return contact;
        }

        if (emailCount >= 2) {
            existingContactWithEmail = emailContact;
        }
        if (phoneCount >= 2)
            existingContactWithPhoneNumber = phoneContact;

        // Search for contacts with matching email and phoneNumber
        if (existingContactWithEmail == null && email != null)
            existingContactWithEmail = contactRepository.findByEmail(email);
        if (existingContactWithPhoneNumber == null && phoneNumber != null) {
            existingContactWithPhoneNumber = contactRepository.findByPhoneNumber(phoneNumber);
        }

        // Both email and phoneNumber do not exist in the database
        if (existingContactWithEmail == null && existingContactWithPhoneNumber == null) {
            // Create a new contact entry with the given details
            Contact newContact = new Contact();
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence(LinkPrecedenceEnum.PRIMARY);
            newContact.setCreatedAt(LocalDateTime.now());
            newContact.setUpdatedAt(LocalDateTime.now());
            newContact.setDeletedAt(null);

            // Save the new contact entry in the database
            return contactRepository.save(newContact);
        }

        // If email or phoneNumber already associated with existing contact then this condition will be true
        if (existingContactWithPhoneNumber != null || existingContactWithEmail != null) {
            Contact newContact = new Contact();
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence(LinkPrecedenceEnum.SECONDARY);
            newContact.setCreatedAt(LocalDateTime.now());
            newContact.setUpdatedAt(LocalDateTime.now());
            newContact.setDeletedAt(null);

            Contact temp = null;
            if (existingContactWithPhoneNumber != null) {
                temp = existingContactWithPhoneNumber;
            } else if (existingContactWithEmail != null) {
                temp = existingContactWithEmail;
            }

            newContact.setLinkedId(temp.getId());
            // save the new contact
            contactRepository.save(newContact);

            Contact response = new Contact();
            response.setId(temp.getId());
            if (temp.getEmail() != "" && temp.getEmail() != null && temp.getEmail().length() != 0)
                response.getEmails().add(temp.getEmail());
            if (temp.getPhoneNumber() != "" && temp.getPhoneNumber() != null && temp.getPhoneNumber().length() != 0)
                response.getPhoneNumbers().add(temp.getPhoneNumber());

            allContacts = contactRepository.findAll();
            for (Contact contact : allContacts) {
                if (temp.getId() == contact.getLinkedId()) {
                    if (contact.getPhoneNumber() != "" && contact.getPhoneNumber() != null && contact.getPhoneNumber().length() != 0)
                        response.getPhoneNumbers().add(contact.getPhoneNumber());
                    if (contact.getEmail() != "" && contact.getEmail() != null && contact.getEmail().length() != 0)
                        response.getEmails().add(contact.getEmail());
                    response.getSecondaryContactIds().add(contact.getId());
                }
            }

            return response;
        }
        return null; // Return null if none of the scenarios match
    }

}