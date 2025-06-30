package fontys_s3_eb.stock_project_individual.UnitTesting.ConvertersTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import fontys_s3_eb.stock_project_individual.Persistence.Entity.UserEntity;
import fontys_s3_eb.stock_project_individual.bussines.EntityConverters.UserConverter;
import fontys_s3_eb.stock_project_individual.domain.UserPackage.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserConverterTest {

    @Mock
    private UserEntity mockUserEntity;

    @Test
    public void convert_ShouldConvertValidUserEntityToUser() {
        // Arrange
        when(mockUserEntity.getUserId()).thenReturn(1L);
        when(mockUserEntity.getUsername()).thenReturn("testUser");
        when(mockUserEntity.getPassword()).thenReturn("password123");
        when(mockUserEntity.getRole()).thenReturn("USER");

        // Act
        User user = UserConverter.convert(mockUserEntity);

        // Assert
        assertEquals(1L, user.getUserId());
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("USER", user.getRole());
    }

    @Test
    public void convert_ShouldHandleNullFieldsInUserEntity() {
        // Arrange
        when(mockUserEntity.getUserId()).thenReturn(1L);
        when(mockUserEntity.getUsername()).thenReturn(null);
        when(mockUserEntity.getPassword()).thenReturn(null);
        when(mockUserEntity.getRole()).thenReturn(null);

        // Act
        User user = UserConverter.convert(mockUserEntity);

        // Assert
        assertEquals(1L, user.getUserId());
        assertEquals(null, user.getUsername());
        assertEquals(null, user.getPassword());
        assertEquals(null, user.getRole());
    }

    @Test
    public void convert_ShouldHandleEmptyFieldsInUserEntity() {
        // Arrange
        when(mockUserEntity.getUserId()).thenReturn(2L);
        when(mockUserEntity.getUsername()).thenReturn("");
        when(mockUserEntity.getPassword()).thenReturn("");
        when(mockUserEntity.getRole()).thenReturn("");

        // Act
        User user = UserConverter.convert(mockUserEntity);

        // Assert
        assertEquals(2L, user.getUserId());
        assertEquals("", user.getUsername());
        assertEquals("", user.getPassword());
        assertEquals("", user.getRole());
    }
}
