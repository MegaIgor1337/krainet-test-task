package krainet.test.service.annotation;

import krainet.test.api.exceptions.TestResultNotFoundException;
import krainet.test.service.impl.TestResultServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IsTestResultExistValidatorTest {
    @Mock
    private TestResultServiceImpl testResultService;
    @InjectMocks
    private IsTestResultExistValidator isTestResultExistValidator;

    @Test
    public void shouldReturnTrueWhenTestResultExist() {
        when(testResultService.isTestResultExist(1L)).thenReturn(true);

        boolean result = isTestResultExistValidator.isValid(1L, null);

        assertTrue(result);
    }

    @Test
    public void shouldReturnExceptionWhenTestResultNotExist() {
        when(testResultService.isTestResultExist(10L)).thenReturn(false);

        assertThrows(TestResultNotFoundException.class, () -> {
            isTestResultExistValidator.isValid(10L, null);
        });
    }
}
