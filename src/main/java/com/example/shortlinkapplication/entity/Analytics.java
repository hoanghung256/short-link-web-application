package com.example.shortlinkapplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "analytics")
public class Analytics {
    @Column(name = "chartID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chartID;

    @Column(name = "totalClick")
    private Integer totalClick;

    @Column(name = "timeClick")
    private Date timeClick;

    @Column(name = "locationID")
    @ManyToOne
    @JoinColumn(name = "locationID")
    private Locations locationID;

    @Column(name = "deviceID")
    @ManyToOne
    @JoinColumn(name = "devices")
    private Devices deviceID;

    @Column(name = "projectID")
    @ManyToOne
    @JoinColumn(name = "projectID")
    private Project projectID;

}
