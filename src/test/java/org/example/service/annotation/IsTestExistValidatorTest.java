package org.example.service.annotation;

import org.example.api.controllers.exceptions.TestNotFoundException;
import org.example.service.impl.TestServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IsTestExistValidatorTest {
    @Mock
    private TestServiceImpl testService;
    @InjectMocks
    private IsTestExistValidator isTestExistValidator;

    @Test
    public void shouldReturnTrue() {
        when(testService.isTestExist(1L)).thenReturn(true);

        boolean result = isTestExistValidator.isValid(1L, null);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalse() {
        when(testService.isTestExist(10L)).thenReturn(false);

        assertThrows(TestNotFoundException.class, () -> {
            isTestExistValidator.isValid(10L, null);
        });
    }
}
