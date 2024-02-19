package com.example.shortlinkapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "project")
public class Project {
    @Column(name = "projectID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectID;

    @Column(name = "projectName", length = 20)
    private String projectName;

    @Column(name = "projectSlug", length = 20)
    private String projectSlug;

    @Column(name = "creationDate")
    private Date creationDate;

    @Column(name = "shortLink", length = 16)
    @ManyToOne
    @JoinColumn(name = "shortLink")
    private Url shortLink;

}
