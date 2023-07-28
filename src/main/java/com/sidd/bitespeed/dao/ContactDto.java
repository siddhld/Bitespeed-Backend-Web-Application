package com.sidd.bitespeed.dao;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContactDto {
    private Long primaryContactId;
    private List<String> emails;
    private List<String> phoneNumbers;
    private List<Long> secondaryContactIds;

    public ContactDto(){
        this.emails = new ArrayList<>();
        this.phoneNumbers = new ArrayList<>();
        this.secondaryContactIds = new ArrayList<>();
    }
}
