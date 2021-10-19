package com.example.demo.repository;

import com.example.demo.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private final EntityManager em;

    public void save(Follow f){
        em.persist(f);
    }

    public void delete(Follow f){
        em.remove(f);
    }

    public List<Follow> findAll(){
        return em.createQuery("select f from Follow f", Follow.class)
                .getResultList();
    }

    public Optional<Follow> checkByFollowingIdAndFollwerId(Long page_id, Long user_id){
        return findAll().stream()
                .filter(f -> f.getFollower().getId().equals(user_id))
                .filter(f -> f.getFollowing().getId().equals(page_id))
                .findFirst();
    }

}
