package com.sidd.bitespeed.model;

import javax.persistence.*;

import com.sidd.bitespeed.constant.LinkPrecedenceEnum;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;
    private String email;

    @Column(name = "linked_id")
    private Long linkedId;

    @Enumerated(EnumType.STRING)
    @Column(name = "link_precedence")
    private LinkPrecedenceEnum linkPrecedence;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ElementCollection
    @CollectionTable(name = "contact_emails", joinColumns = @JoinColumn(name = "contact_id"))
    @Column(name = "email")
    private Set<String> emails = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "contact_phone_numbers", joinColumns = @JoinColumn(name = "contact_id"))
    @Column(name = "phone_number")
    private Set<String> phoneNumbers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "secondary_contacts", joinColumns = @JoinColumn(name = "contact_id"))
    @Column(name = "secondary_id")
    private Set<Long> secondaryContactIds = new HashSet<>();

}