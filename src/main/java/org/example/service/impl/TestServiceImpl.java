package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.persistence.entity.Direction;
import org.example.persistence.entity.Test;
import org.example.persistence.repository.TestRepository;
import org.example.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    @Override
    public boolean isTestExist(Long id) {
        return testRepository.existsById(id);
    }

    @Override
    public Test findTestById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

}
