package hw2;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PercolationTests {

    @Test
    public void newGridShouldReturnZeroOpenSites() {
        // Arrange
        Percolation grid = new Percolation(2);

        // Act
        int actual = grid.numberOfOpenSites();

        // Assert
        assertEquals(0, actual);
    }

    @Test
    public void newGridShouldHaveNoFullSites() {
        // Arrange
        Percolation grid = new Percolation(2);

        // Act
        boolean actual00 = grid.isFull(0, 0);
        boolean actual01 = grid.isFull(0, 1);
        boolean actual10 = grid.isFull(1, 0);
        boolean actual11 = grid.isFull(1, 1);

        // Assert
        assertFalse(actual00);
        assertFalse(actual01);
        assertFalse(actual10);
        assertFalse(actual11);
    }

    @Test
    public void newGridShouldHaveNoOpenSites() {
        // Arrange
        Percolation grid = new Percolation(2);

        // Act
        boolean actual00 = grid.isOpen(0, 0);
        boolean actual01 = grid.isOpen(0, 1);
        boolean actual10 = grid.isOpen(1, 0);
        boolean actual11 = grid.isOpen(1, 1);

        // Assert
        assertFalse(actual00);
        assertFalse(actual01);
        assertFalse(actual10);
        assertFalse(actual11);
    }

    @Test
    public void openSiteShouldReturnIsOpenTrue() {
        // Arrange
        Percolation grid = new Percolation(1);
        grid.open(0, 0);

        // Act
        boolean actual = grid.isOpen(0, 0);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void openSiteShouldReturnIsFullTrue() {
        // Arrange
        Percolation grid = new Percolation(1);
        grid.open(0, 0);

        // Act
        boolean actual = grid.isFull(0, 0);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void openSiteShouldReturnNumberOpenSites() {
        // Arrange
        Percolation grid = new Percolation(1);
        grid.open(0, 0);

        // Act
        int actual = grid.numberOfOpenSites();

        // Assert
        assertEquals(1, actual);
    }

    @Test
    public void closedSitesShouldntPercolate() {
        // Arrange
        Percolation grid = new Percolation(2);

        // Act
        boolean actual = grid.percolates();

        // Assert
        assertFalse(actual);
    }

    @Test
    public void openSitesShouldPercolate() {
        // Arrange
        Percolation grid = new Percolation(2);
        grid.open(0, 0);
        grid.open(1, 0);

        // Act
        boolean actual = grid.percolates();

        // Assert
        assertTrue(actual);

    }

    @Test
    public void percolationShouldntBackflow() {
        // Arrange
        Percolation grid = new Percolation(3);
        grid.open(0, 2);
        grid.open(1, 2);
        grid.open(2, 2);
        grid.open(2, 0);

        // Act
        boolean actual = grid.isFull(2,0);

        // Assert
        assertFalse(actual);
    }

    @Test
    public void waterShouldFlow() {
        // Arrange
        Percolation grid = new Percolation(3);
        grid.open(0, 2);
        grid.open(1, 2);
        grid.open(2, 2);

        // Act
        boolean actual = grid.isFull(2, 2);

        // Assert
        assertTrue(actual);
    }

    private static Percolation generatePercolationFromFile(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        int grid_dimensions = sc.nextInt();
        Percolation grid = new Percolation(grid_dimensions);
        while (sc.hasNextInt()) {
            int row = sc.nextInt();
            int col = sc.nextInt();
            grid.open(row, col);
        }
        return grid;
    }

    @Test
    public void testInput2() throws FileNotFoundException {
        // Arrange
        Percolation grid = generatePercolationFromFile("inputFiles/input2.txt");

        // Act
        boolean is_percolating = grid.percolates();
        int open_sites = grid.numberOfOpenSites();

        // Assert
        assertTrue(is_percolating);
        assertEquals(3, open_sites);
    }

    @Test
    public void testInput2No() throws FileNotFoundException {
        // Arrange
        Percolation grid = generatePercolationFromFile("inputFiles/input2-no.txt");

        // Act
        boolean is_percolating = grid.percolates();
        int open_sites = grid.numberOfOpenSites();

        // Assert
        assertFalse(is_percolating);
        assertEquals(2, open_sites);
    }

    @Test
    public void testInput4() throws FileNotFoundException {
        // Arrange
        Percolation grid = generatePercolationFromFile("inputFiles/input4.txt");

        // Act
        boolean is_percolating = grid.percolates();
        int open_sites = grid.numberOfOpenSites();

        // Assert
        assertTrue(is_percolating);
        assertEquals(8, open_sites);
    }

    @Test
    public void testInput8() throws FileNotFoundException {
        // Arrange
        Percolation grid = generatePercolationFromFile("inputFiles/input8.txt");

        // Act
        boolean is_percolating = grid.percolates();
        int open_sites = grid.numberOfOpenSites();

        // Assert
        assertTrue(is_percolating);
        assertEquals(34, open_sites);
    }

    @Test
    public void testInput8No() throws FileNotFoundException {
        // Arrange
        Percolation grid = generatePercolationFromFile("inputFiles/input8-no.txt");

        // Act
        boolean is_percolating = grid.percolates();
        int open_sites = grid.numberOfOpenSites();

        // Assert
        assertFalse(is_percolating);
        assertEquals(33, open_sites);
    }

    @Test
    public void testInput10() throws FileNotFoundException {
        // Arrange
        Percolation grid = generatePercolationFromFile("inputFiles/input10.txt");

        // Act
        boolean is_percolating = grid.percolates();
        int open_sites = grid.numberOfOpenSites();

        // Assert
        assertTrue(is_percolating);
        assertEquals(56, open_sites);
    }

    @Test
    public void testInput10No() throws FileNotFoundException {
        // Arrange
        Percolation grid = generatePercolationFromFile("inputFiles/input10-no.txt");

        // Act
        boolean is_percolating = grid.percolates();
        int open_sites = grid.numberOfOpenSites();

        // Assert
        assertFalse(is_percolating);
        assertEquals(55, open_sites);
    }

    @Test
    public void testInput20() throws FileNotFoundException {
        // Arrange
        Percolation grid = generatePercolationFromFile("inputFiles/input20.txt");

        // Act
        boolean is_percolating = grid.percolates();
        int open_sites = grid.numberOfOpenSites();

        // Assert
        assertTrue(is_percolating);
        assertEquals(250, open_sites);
    }

    @Test
    public void testInput50() throws FileNotFoundException {
        // Arrange
        Percolation grid = generatePercolationFromFile("inputFiles/input50.txt");

        // Act
        boolean is_percolating = grid.percolates();
        int open_sites = grid.numberOfOpenSites();

        // Assert
        assertTrue(is_percolating);
        assertEquals(1412, open_sites);
    }
}
