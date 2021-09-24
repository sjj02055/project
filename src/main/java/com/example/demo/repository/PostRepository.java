package com.example.demo.repository;

import com.example.demo.domain.Post;
import com.example.demo.domain.Post_image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post){
        em.persist(post);
    }

    public void piSave(Post_image pi){
        em.persist(pi);
    }
    public List<Post> findByUserIdOrderByIdDesc(long id){
        return em.createQuery("select p from Post p where p.member.id=:id" , Post.class)
                .getResultList();
    }

    public List<Post_image> findByPostId(long id){
        return em.createQuery("select p from Post_image p where p.id =: id", Post_image.class)
                .getResultList();
    }

}
