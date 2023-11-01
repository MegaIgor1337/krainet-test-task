package krainet.test.service.annotation;

import krainet.test.api.exceptions.DirectionNotFoundException;
import krainet.test.service.impl.DirectionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IsDirectionExistValidatorTest {
    @Mock
    private DirectionServiceImpl directionService;

    @InjectMocks
    private IsDirectionExistValidator isDirectionExistValidator;

    @Test
    public void shouldReturnTrue() {
        when(directionService.isDirectionExist(1L)).thenReturn(true);

        boolean result = isDirectionExistValidator.isValid(1L, null);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalse() {
        when(directionService.isDirectionExist(10L)).thenReturn(false);

        assertThrows(DirectionNotFoundException.class, () -> {
            isDirectionExistValidator.isValid(10L, null);
        });
    }
}
