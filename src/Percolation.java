/**
 * Created with IntelliJ IDEA.
 * User: vikas2284
 * Date: 17/2/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class Percolation {

    private final int N; // Length of one side of the grid.
    private boolean[] open;
    private WeightedQuickUnionUF percolation;
    private WeightedQuickUnionUF fullness;
    private final int virtualTop;
    private final int virtualBottom;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        // The union-find data type we're using indexes its arrays with an int,
        // so our N^2 sized grid must have N^2 <= 2^32 - 1 <=> N > 2^16.
        // N^2 <= 2^32 - 3 <-> N^2 < 2^32 - 2 -> N < 0xffff
        if (N >= 0xffff)
            throw new IllegalArgumentException("Dimension must be < 2^16");
        this.N = N;
        open = new boolean[N*N];
        // Add two for the virtual top and bottom
        percolation = new WeightedQuickUnionUF(N*N + 2);
        fullness = new WeightedQuickUnionUF(N*N + 2);
        virtualTop = indexOf(N, N) + 1;
        virtualBottom = indexOf(N, N) + 2;
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return open[indexOf(i, j)];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return fullness.connected(virtualTop, indexOf(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return percolation.connected(virtualTop, virtualBottom);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (isOpen(i, j))
            return;
        int index = indexOf(i, j);

        open[index] = true;

        if (i == 1) {
            percolation.union(virtualTop, index);
            fullness.union(virtualTop, index);
        }
        if (i == N)
            percolation.union(virtualBottom, index);

        if (i < N  && isOpen(i + 1, j)) {
            percolation.union(indexOf(i + 1, j), index);
            fullness.union(indexOf(i + 1, j), index);
        }
        if (i > 1 && isOpen(i - 1, j)) {
            percolation.union(indexOf(i - 1, j), index);
            fullness.union(indexOf(i - 1, j), index);
        }

        if (j < N && isOpen(i, j + 1)) {
            percolation.union(indexOf(i, j + 1), index);
            fullness.union(indexOf(i, j + 1), index);
        }
        if (j > 1 && isOpen(i, j - 1)) {
            percolation.union(indexOf(i, j - 1), index);
            fullness.union(indexOf(i, j - 1), index);
        }
    }

    /* Convert grid coordinates of the form (x, y) where x,y in {1,...,N}
    to an array index. E.g., indexOf(1,1) == 0; indexOf(N, N) = N^2 - 1.

    Assume the grid is in row-major form.
    */
    private int indexOf(int row, int col) {
        if (row <= 0 || row > N || col <= 0 || col > N)
            throw new IndexOutOfBoundsException(
                    "(" + row + ", " + col + ") out of bounds "
                            + "for " + N + "^2 grid.");
        return (row - 1) * N + (col - 1);
    }
}