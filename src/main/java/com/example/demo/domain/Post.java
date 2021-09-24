package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private Member member;

    private String description;
    private String location;

    private Timestamp create_date;
    private Timestamp update_date;

}
