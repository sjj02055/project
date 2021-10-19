package com.example.demo.service;

import com.example.demo.domain.Follow;
import com.example.demo.repository.FollowRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {

    private FollowRepository followRepository;
    private MemberRepository memberRepository;


    @Transactional
    public void follow(Follow f){
        followRepository.save(f);
    }

    @Transactional
    public void unfollow(Follow f){
        followRepository.delete(f);
    }

    public Follow find(Long page_id, Long user_id){
        return followRepository.checkByFollowingIdAndFollwerId(page_id, user_id).orElse(null);
    }

}
