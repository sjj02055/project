package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter @Setter
public class Member {

    @GeneratedValue
    @Column(name="member_id")
    @Id
    private Long id;

    private String nickname;

    private String userId;

    private String password;

    private String name;

    private int year,month,day;

    private String profile_photo;

    private String introduce;

}
