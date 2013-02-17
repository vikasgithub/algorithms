/**
 * Created with IntelliJ IDEA.
 * User: vikas2284
 * Date: 17/2/13
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class PercolationStats {

    private int N;
    private int T;
    private double[] simulationResults;

    public PercolationStats(int N, int T) {
        if (N <= 0) {
            throw new IllegalArgumentException("N should be more then zero");
        }
        if (T <= 0) {
            throw new IllegalArgumentException("T should be more then zero");
        }

        this.N = N;
        this.T = T;
        simulationResults = new double[T];

        for (int t = 0; t < T; t++) {
            simulationResults[t] = runSimulation();
        }
    }

    private double runSimulation() {
        Percolation percolation = new Percolation(N);

        double counter = 0;
        while (!percolation.percolates()) {
            counter++;

            int i = StdRandom.uniform(1, N + 1);
            int j = StdRandom.uniform(1, N + 1);

            //generate new random sites until a blocked one is found
            while (percolation.isOpen(i, j)) {
                i = StdRandom.uniform(1, N + 1);
                j = StdRandom.uniform(1, N + 1);

            }

            percolation.open(i, j);
        }

        return counter / (N * N);
    }


    public double mean() {
        return StdStats.mean(simulationResults);
    }

    public double stddev() {
        return StdStats.stddev(simulationResults);
    }

    public static void main(String[] args) {
        int gridSize;
        int simulationCount;

        if (args.length == 0) {
            gridSize = 1000;
            simulationCount = 100;
        } else {
            gridSize = Integer.parseInt(args[0]);
            simulationCount = Integer.parseInt(args[1]);
        }

        if (gridSize <= 0 || simulationCount <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "Arguments must be greater than 0");
        }


        PercolationStats percSim =
                new PercolationStats(gridSize, simulationCount);
        double confidenceLo = percSim.confidenceLo();
        double confidenceHi = percSim.confidenceHi();


        System.out.println("mean                    = "
                + percSim.mean());
        System.out.println("stddev                  = "
                + percSim.stddev());
        System.out.println("95% confidence interval = "
                + confidenceLo + ", " + confidenceHi);
    }

    public double confidenceHi() {
        return mean() + 1.96
                    * stddev() / Math.sqrt(T);
    }

    public double confidenceLo() {
        return mean() - 1.96
                    * stddev() / Math.sqrt(T);
    }
}