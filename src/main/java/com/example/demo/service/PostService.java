package com.example.demo.service;

import com.example.demo.domain.Post;
import com.example.demo.domain.Post_image;
import com.example.demo.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(Post p){
        postRepository.save(p);
        return p.getId();
    }

    @Transactional
    public Long piSave(Post_image p){
        postRepository.piSave(p);
        return p.getId();
    }

    public List<Post> findByUserIdOrderByIdDesc(long id){
        return postRepository.findByUserIdOrderByIdDesc(id);
    }

    public List<Post_image> findByPostid(){
        return postRepository.findByPostId();
    }

}
