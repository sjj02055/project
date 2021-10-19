package com.example.demo.repository;

import com.example.demo.domain.Follow;
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

    public void delete(Member member){
        em.remove(member);
    }

    //id로 한명 찾는 메소드
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    //가입되어있는 모든 멤버 불러오는 메소드
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //userId를 통해 한명 찾아오기
    public Optional<Member> findOneByUserId(String userId){
        return findAll().stream()
                .filter(m ->m.getUserId().equals(userId))
                .findFirst();
    }

    //userId를 통해 여러명 찾아오기
    public List<Member> findByUserId(String userId){
        return em.createQuery("select m from Member m where m.userId= :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
