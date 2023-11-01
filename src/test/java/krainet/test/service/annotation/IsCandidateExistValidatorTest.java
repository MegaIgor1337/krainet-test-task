package krainet.test.service.annotation;

import krainet.test.api.exceptions.CandidateNotFoundException;
import krainet.test.service.impl.CandidateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IsCandidateExistValidatorTest {
    @Mock
    private CandidateServiceImpl candidateService;
    @InjectMocks
    private IsCandidateExistValidator isCandidateExistValidator;

    @Test
    public void shouldReturnTrue() {
        when(candidateService.isCandidateExist(1L)).thenReturn(true);

        boolean result = isCandidateExistValidator.isValid(1L, null);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalse() {
        when(candidateService.isCandidateExist(10L)).thenReturn(false);

        assertThrows(CandidateNotFoundException.class, () -> {
            isCandidateExistValidator.isValid(10L, null);
        });
    }
}
