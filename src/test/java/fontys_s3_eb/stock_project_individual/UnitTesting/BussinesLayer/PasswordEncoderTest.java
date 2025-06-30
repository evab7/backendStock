package fontys_s3_eb.stock_project_individual.UnitTesting.BussinesLayer;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class PasswordEncoderTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void encode_ShouldReturnEncodedPassword() {
        // Arrange
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword123";
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // Act
        String result = passwordEncoder.encode(rawPassword);

        // Assert
        assertEquals(encodedPassword, result);
    }

    @Test
    public void encode_ShouldReturnEncodedPassword_EmptyPassword() {
        // Arrange
        String rawPassword = "";
        String encodedPassword = "encodedEmptyPassword";
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // Act
        String result = passwordEncoder.encode(rawPassword);

        // Assert
        assertEquals(encodedPassword, result);
    }

    @Test
    public void encode_ShouldReturnDifferentEncodedPasswordsForDifferentInputs() {
        // Arrange
        String password1 = "password123";
        String password2 = "anotherPassword";
        String encodedPassword1 = "encodedPassword123";
        String encodedPassword2 = "encodedAnotherPassword";

        when(passwordEncoder.encode(password1)).thenReturn(encodedPassword1);
        when(passwordEncoder.encode(password2)).thenReturn(encodedPassword2);

        // Act
        String result1 = passwordEncoder.encode(password1);
        String result2 = passwordEncoder.encode(password2);

        // Assert
        assertEquals(encodedPassword1, result1);
        assertEquals(encodedPassword2, result2);
    }
}
