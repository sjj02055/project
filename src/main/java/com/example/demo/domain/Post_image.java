package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Post_image {

    @Id
    @GeneratedValue
    Long id;

    Long postId;

    String filename;
    String fileorImage;

}
