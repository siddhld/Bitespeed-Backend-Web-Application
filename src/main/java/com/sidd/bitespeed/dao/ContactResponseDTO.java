package com.sidd.bitespeed.dao;

import com.sidd.bitespeed.model.Contact;
import lombok.Data;

@Data
public class ContactResponseDTO {
    ContactDto contact;

    public ContactResponseDTO(Contact consolidatedContact) {
//        System.err.println("consolidatedContact ----------> " + consolidatedContact);

        contact = new ContactDto();

        this.contact.setPrimaryContactId(consolidatedContact.getId());

        if (consolidatedContact.getEmails().size() != 0) {
            for (String email : consolidatedContact.getEmails().stream().toList()) {
                if (email != null)
                    this.contact.getEmails().add(email);
            }
        } else {
            if (consolidatedContact.getEmail() != null)
                this.contact.getEmails().add(consolidatedContact.getEmail());
        }

        if (consolidatedContact.getPhoneNumbers().size() != 0) {
            for (String phoneNUmber : consolidatedContact.getPhoneNumbers().stream().toList()) {
                if (phoneNUmber != null)
                    this.contact.getPhoneNumbers().add(phoneNUmber);
            }
        } else {
            if (consolidatedContact.getPhoneNumber() != null)
                this.contact.getPhoneNumbers().add(consolidatedContact.getPhoneNumber());
        }

        this.contact.getSecondaryContactIds().addAll(consolidatedContact.getSecondaryContactIds().stream().toList());

    }
}