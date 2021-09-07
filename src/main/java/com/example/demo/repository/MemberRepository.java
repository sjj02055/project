package com.example.demo.repository;

import com.example.demo.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findOneByUserId(String userId){
        return findAll().stream()
                .filter(m ->m.getUserId().equals(userId))
                .findFirst();
    }

    public List<Member> findByUserId(String userId){
        return em.createQuery("select m from Member m where m.userId= :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
