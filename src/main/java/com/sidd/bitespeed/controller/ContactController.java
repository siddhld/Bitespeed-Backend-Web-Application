package com.sidd.bitespeed.controller;

import com.sidd.bitespeed.dao.ContactRequestDTO;
import com.sidd.bitespeed.dao.ContactResponseDTO;
import com.sidd.bitespeed.model.Contact;
import com.sidd.bitespeed.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/identify")
    public ResponseEntity<ContactResponseDTO> identifyContact(@RequestBody ContactRequestDTO requestDTO) {
        Contact consolidatedContact = contactService.identifyContact(requestDTO);
        if (consolidatedContact != null) {
            // Converting the Contact object to ContactResponseDTO
            ContactResponseDTO responseDTO = new ContactResponseDTO(consolidatedContact);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}