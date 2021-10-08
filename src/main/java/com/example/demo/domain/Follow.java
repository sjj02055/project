package com.example.demo.domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@DynamicUpdate
public class Follow {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "following")
    Member following;

    @ManyToOne
    @JoinColumn(name = "follower")
    Member follower;

    Timestamp followDate;
}