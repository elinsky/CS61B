package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int number_of_open_sites;
    WeightedQuickUnionUF connected;
    boolean[] open;
    int N;
    int water_source;

    // create N-by-N grid, with all sites initially blocked
    // This should take N^2 time
    public Percolation(int N) {
        this.N = N;
        this.water_source = N * N;
        this.number_of_open_sites = 0;
        this.connected = new WeightedQuickUnionUF(N * N + 1);
        this.open = new boolean[N * N];
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {

    }

    // open the site (row, col) if it is not open already
    // must take constant time + calls to union(), find(), connected(), count()
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        int cell = (row * N) + col;
        open[cell] = true;
        if (row == 0) {
            connected.union(water_source, cell);
        }
        // Are you next to other open cells?  Then connect.
        // Check top cell
        if (row > 0 && open[cell - N]) {
            connected.union(cell, cell - N);
        }

        // Check bottom cell
        if (row + 1 < N && open[cell + N]) {
            connected.union(cell, cell + N);
        }

        // Check left cell
        if (col > 0 && open[cell - 1]) {
            connected.union(cell, cell - 1);
        }

        // Check right cell
        if (col < N - 1 && open[cell + 1]) {
            connected.union(cell, cell + 1);
        }
        number_of_open_sites += 1;
    }

    // is the site (row, col) open?
    // must take constant time + calls to union(), find(), connected(), count()
    public boolean isOpen(int row, int col) {
        return open[(row * N) + col];
    }

    // is the site (row, col) full?
    // must take constant time + calls to union(), find(), connected(), count()
    public boolean isFull(int row, int col) {
        int cell = (row * N) + col;
        return connected.find(water_source) == connected.find(cell);
    }

    // number of open sites
    // must take constant time + calls to union(), find(), connected(), count()
    public int numberOfOpenSites() {
        return number_of_open_sites;
    }

    // does the system percolate?
    // must take constant time + calls to union(), find(), connected(), count()
    public boolean percolates() {
        // TODO - probably a performance improvement here.  I think this is currently N * log(N).
        int bottom_row_start = (N - 1) * N;
        int bottom_row_end = bottom_row_start + N - 1;
        for (int i = bottom_row_start; i <= bottom_row_end; i++) {
            if (open[i] && connected.find(water_source) == connected.find(i)) {
                return true;
            }
        }
        return false;
    }
}
