package com.example.demo.service;

import com.example.demo.domain.Follow;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member){
        ValidateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void ValidateDuplicateMember(Member member) {
        List<Member> findMembers =memberRepository.findByUserId(member.getUserId());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //회원 한명 조회
    public Member findOne(String memberId){
        Member findMember = memberRepository.findOneByUserId(memberId).orElse(null);

        return findMember;
    }

    public Member find(Long id){
        Member findMember = memberRepository.findOne(id);
        return findMember;
    }

    public Member login(String userId, String password){

        Member findMember = memberRepository.findOneByUserId(userId)
                                            .filter(m -> m.getPassword().equals(password))
                                            .orElse(null);
        if(findMember.getPassword().equals(password)){
            return findMember;
        }
        else{
            return null;
        }
    }

    @Transactional
    public void imgUpdate(Long id, String profile_photo){
        Member findMember = memberRepository.findOne(id);
        findMember.setProfile_photo(profile_photo);
        memberRepository.save(findMember);
    }

    @Transactional
    public void update(Member member){
        Member findMember = memberRepository.findOne(member.getId());
        findMember.setIntroduce(member.getIntroduce());
        findMember.setNickname(member.getNickname());
        findMember.setName(member.getName());

        memberRepository.save(findMember);
    }

    public List<Member> findByContainingUserId(String word){
        return memberRepository.findByContainingUserId(word);
    }

    public int countByContainingUserId(String word){
        return memberRepository.countByContainingUserId(word);
    }
}