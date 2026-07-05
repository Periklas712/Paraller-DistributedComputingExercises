import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class NumIntSeqForkExecutor {

  /*
      numSteps       |   limit ΓΙΑ 16 ΕΡΓΑΣΙΕΣ |    limit ΓΙΑ 32 ΕΡΓΑΣΙΕΣ   | limit ΓΙΑ 64 ΕΡΓΑΣΙΕΣ  

      10 ΕΚΑΤΟΜΥΡ    |         625_000         |           312_500          |        156_250
    ΧΡΟΝΟΣ ΣΕ SECOND |        0.055000         |          0.058000          |        0.048000

      100 ΕΚΑΤΟΜΥΡ   |        6_250_000        |         3_125_000          |        1_562_500
    ΧΡΟΝΟΣ ΣΕ SECOND |         0.093000        |         0.078000           |        0.093000

         1 ΔΙΣ       |        62_500_000       |         31_250_000         |	    15_625_000
    ΧΡΟΝΟΣ ΣΕ SECOND |         0.299000        |          0.289000          |        0.281000
    */

    private static double ComputePiUsingForkPool() {
        long numSteps =1_000_000_000;
        double step =1.0 / (double) numSteps;
        long limit =15_625_000;

        ForkJoinPool pool = new ForkJoinPool(); 
        ComputePiTask task = new ComputePiTask(0, numSteps, limit, step);
        double sum = pool.invoke(task);
        pool.shutdown();
        
        return sum * step; 
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        double pi = ComputePiUsingForkPool();

        long endTime = System.currentTimeMillis();
        System.out.printf("computed pi = %22.20f\n", pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double)(endTime - startTime) / 1000);
    }
}

class ComputePiTask extends RecursiveTask<Double> {
    private long myStart;
    private long myStop;
    private long limit;
    private double step;

    public ComputePiTask(long myStart, long myStop, long limit, double step) {
        this.myStart = myStart;
        this.myStop = myStop;
        this.limit = limit;
        this.step = step;
    }

    @Override
    protected Double compute() {
        double sum = 0.0;
        long range = myStop - myStart;

        if (range < limit) {
            for (long i = myStart; i < myStop; i++) {
                double x = ((double)i + 0.5) * step;
                sum += 4.0 / (1.0 + x * x);
            }
        } else {
            long mid = myStart + range / 2;
            ComputePiTask left = new ComputePiTask(myStart, mid, limit, step);
            ComputePiTask right = new ComputePiTask(mid, myStop, limit, step);

            left.fork();
            double rightResult = right.compute();
            double leftResult = left.join();
            sum = leftResult + rightResult;
        }

        return sum;
    }
}
