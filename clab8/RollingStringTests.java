import org.junit.Test;
import static org.junit.Assert.*;

public class RollingStringTests {
    @Test
    public void addCharShouldShiftStringRight() {
        // Arrange
        String initial = "dog";
        RollingString rs = new RollingString(initial, 3);

        // Act
        rs.addChar(' ');

        // Assert
        String expected = "og ";
        assertEquals(expected, rs.toString());
    }

    @Test
    public void toStringShouldReturnString() {
        // Arrange
        String initial = "dog";
        RollingString rs = new RollingString(initial, 3);

        // Act
        String actual = rs.toString();

        // Assert
        String expected = "dog";
        assertEquals(expected, actual);
    }

    @Test
    public void DogLengthShouldReturnThree() {
        // Arrange
        String initial = "dog";
        RollingString rs = new RollingString(initial, 3);

        // Act
        int actual = rs.length();

        // Assert
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    public void DogShouldEqualDog() {
        // Arrange
        String initial = "dog";
        RollingString rs1 = new RollingString(initial, 3);
        RollingString rs2 = new RollingString(initial, 3);

        // Act
        boolean actual = rs1.equals(rs2);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void HashesShouldBeEqual() {
        // Arrange
        String initial = "dog";
        RollingString rs1 = new RollingString(initial, 3);
        rs1.addChar(' ');
        String initial2 = "og ";
        RollingString rs2 = new RollingString(initial2, 3);

        // Act
        int hash1 = rs1.hashCode();
        int hash2 = rs2.hashCode();

        // Assert
        assertEquals(hash1, hash2);
    }
}

