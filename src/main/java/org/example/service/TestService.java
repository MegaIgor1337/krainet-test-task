package org.example.service;

import org.example.persistence.entity.Direction;
import org.example.persistence.entity.Test;

public interface TestService {
    boolean isTestExist(Long id);
    Test findTestById(Long id);
}
