package com.example.demo.repository;

import com.example.demo.domain.cctv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class apiRepository {

    private final EntityManager em;

    public void save(cctv cctv){em.persist(cctv);}

    public cctv findOne(Long id) { return em.find(cctv.class, id);}

    public List<cctv> findAll(){
        return em.createQuery("select c from cctv c", cctv.class)
                .getResultList();
    }

}
