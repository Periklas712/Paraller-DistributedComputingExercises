import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pi_ArrayPartialLocalSum_NoLock {

    /*
    ΜΕΓΕΘΟΣ ΠΙΝΑΚΑ   |   Ακολουθιακά    |      2    |   16   ΝΗΜΑΤΑ
        1εκ          |    0.008000      |  0.010000 | 0.028000   ΧΡΟΝΟΣ ΣΕ sec
        10εκ         |    0.031000      |  0.023000 |  0.038000  ΧΡΟΝΟΣ ΣΕ sec
        100εκ        |    0.286000      |  0.140000 |  0.053000  ΧΡΟΝΟΣ ΣΕ sec
        1δις         |    2.498000      |  1.373000 |  0.284000  ΧΡΟΝΟΣ ΣΕ sec -->ακραία βελτίωση

         */

    public static void main(String[] args) {

        long numSteps = 1000000000;
        int numThreads=16;
        double sum=0.0;

        /* parse command line
        if (args.length != 1) {
		System.out.println("arguments:  number_of_steps");
                System.exit(1);
        }
        try {
		numSteps = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
		System.out.println("argument "+ args[0] +" must be long int");
		System.exit(1);
        }*/

        //ΦΤΙΑΧΝΩ ΤΑ ΝΗΜΑΤΑ
        MyThread threads[] = new MyThread[numThreads];

        //ΘΑ ΦΤΙΑΞΩ ΕΝΑΝ ΠΙΝΑΚΑ ΜΕ ΤΑ ΜΕΡΙΚΑ ΑΘΡΟΙΣΜΑΤΑ ΚΑΘΕ ΝΗΜΑΤΟΣ
        //ΣΤΟ ΤΕΛΟΣ ΘΑ ΑΘΡΟΙΣΩ ΤΑ ΜΕΡΙΚΑ ΑΥΤΑ ΑΘΡΟΙΣΜΑΤΑ ΣΤΟ SUM
        double[] partialSums= new double[numThreads];

        //ΧΩΡΙΖΩ ΤΟ ΚΟΜΜΑΤΙ ΠΟΥ ΘΑ ΠΑΡΕΙ ΚΑΘΕ ΝΗΜΑ
        double step = 1.0 / (double)numSteps;
        long block = numSteps/numThreads;
        long start=0;
        long stop=0;

        /* start timing */
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            start = i*block;
            stop = i*block+block;
            if (i == numThreads - 1) stop = numSteps;
            threads[i] = new MyThread(i,start,stop,step,partialSums);
            threads[i].start();
        }

        try {
            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < numThreads; i++) {
            sum += partialSums[i];
        }

        double pi = sum * step;

        /* end timing and print result */
        long endTime = System.currentTimeMillis();
        System.out.printf("sequential program results with %d steps\n", numSteps);
        System.out.printf("computed pi = %22.20f\n" , pi);
        System.out.printf("difference between estimated pi and Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("time to compute = %f seconds\n", (double) (endTime - startTime) / 1000);

    }
}

class MyThread extends Thread {
    private int index;
    private long myStart, myStop;
    private double myStep;
    private double partialSum = 0.0;
    private double[] partialSums;


    public MyThread(int id, long start, long stop, double step, double[] partialSums) {
        index = id;
        myStart = start;
        myStop = stop;
        myStep = step;
        this.partialSums = partialSums;

    }

    public void run() {
        for (long i = myStart; i < myStop; ++i) {
            double x = ((double) i + 0.5) * myStep;
            partialSum += 4.0 / (1.0 + x * x);
        } //ΚΑΘΕ ΝΗΜΑ ΥΠΟΛΟΓΙΖΕΙ ΤΟ ΤΟΠΙΚΟ ΑΘΡΟΙΣΜΑ
        partialSums[index] = partialSum;
    }
}


