package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class cctv {

    @Id @GeneratedValue
    @Column(name = "cctv_id")
    private Long id;

    private String institutionNm;

    private String latitude;

    private String longitude;
}
