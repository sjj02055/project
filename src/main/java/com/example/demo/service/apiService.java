package com.example.demo.service;

import com.example.demo.domain.cctv;
import com.example.demo.repository.apiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class apiService {

    private final apiRepository apiRepository;

    @Transactional
    public Long join(cctv cctv){
        apiRepository.save(cctv);
        return cctv.getId();
    }

    public List<cctv> findcctvs(){
        return apiRepository.findAll();
    }

}
