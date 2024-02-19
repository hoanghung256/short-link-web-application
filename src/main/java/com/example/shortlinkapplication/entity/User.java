package com.example.shortlinkapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "user")
public class User {
    @Column(name = "userID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "email", length = 32)
    private String email;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "lastLogin")
    private Date lastLogin;
}
