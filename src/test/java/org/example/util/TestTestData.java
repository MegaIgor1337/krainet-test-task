package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.persistence.entity.Test;

@UtilityClass
public class TestTestData {
    public static Test createTest() {
        return Test.builder()
                .id(1L)
                .name("Test")
                .description("Test")
                .build();
    }
}
