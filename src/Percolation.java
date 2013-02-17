/**
 * Created with IntelliJ IDEA.
 * User: vikas2284
 * Date: 17/2/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Percolation {

    private int N;
    private int[] grid;
    private WeightedQuickUnionUF UF;
    private int virtualTopSitePosition;
    private int virtualBottomSitePosition;

    public Percolation(int N) {
        this.N = N;
        grid = new int[N * N];

        //every cell plus virtual top site and virtual bottom site
        UF = new WeightedQuickUnionUF(N * N + 2);
        virtualTopSitePosition = N * N;
        virtualBottomSitePosition = N * N + 1;

        //union each site in top & bottom rows to respective virtual sites
        for (int j = 1; j < N; j++) {
            UF.union(coordinatesToPosition(1, j), virtualTopSitePosition);
            UF.union(coordinatesToPosition(N, j), virtualBottomSitePosition);
        }
    }

    private int coordinatesToPosition(int i, int j) {
        checkInBounds(i, j);
        return N * i - (N - j + 1);
    }

    private void checkInBounds(int i, int j) {
        if (!exists(i, j))
            throw new IndexOutOfBoundsException("grid index out of bounds");
    }

    public boolean isOpen(int i, int j) {
        checkInBounds(i, j);
        if (grid[coordinatesToPosition(i, j)] != 0) {
            return true;
        }
        return false;

    }

    public boolean isFull(int i, int j) {
        checkInBounds(i, j);
        if (grid[coordinatesToPosition(i, j)] == 0) {
            return true;
        }
        return false;

    }

    public void open(int i, int j) {
        checkInBounds(i, j);
        this.grid[coordinatesToPosition(i, j)] = 1;

        if (exists(i - 1, j) && isOpen(i - 1, j)) {
            UF.union(coordinatesToPosition(i, j),
                    coordinatesToPosition(i - 1, j));
        }

        if (exists(i + 1, j) && isOpen(i + 1, j)) {
            UF.union(coordinatesToPosition(i, j),
                    coordinatesToPosition(i + 1, j));
        }

        if (exists(i, j - 1) && isOpen(i, j - 1)) {
            UF.union(coordinatesToPosition(i, j),
                    coordinatesToPosition(i, j - 1));
        }

        if (exists(i, j + 1) && isOpen(i, j + 1)) {
            UF.union(coordinatesToPosition(i, j),
                    coordinatesToPosition(i, j + 1));
        }

    }

    private boolean exists(int i, int j) {
        if (i <= 0 || i > N) {
            return false;
        }
        if (j <= 0 || j > N) {
            return false;
        }
        return true;
    }

    public boolean percolates() {
        if (UF.connected(virtualTopSitePosition, virtualBottomSitePosition)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(4);
        System.out.println("percolates? " + perc.percolates());
        System.out.println(perc.isFull(2, 2));
        perc.open(2, 2);
        System.out.println(perc.isFull(2, 2));

        perc.open(1, 2);
        perc.open(3, 2);
        perc.open(4, 2);

        System.out.println(perc.grid.length);
        System.out.println("percolates? " + perc.percolates());
    }
}

