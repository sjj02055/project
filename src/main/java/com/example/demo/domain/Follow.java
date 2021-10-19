package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@DynamicUpdate
@Getter @Setter
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