import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pi_IncorrectLock {

    /*
    ΜΕΓΕΘΟΣ ΠΙΝΑΚΑ   |   Ακολουθιακά    |      2    |   16   ΝΗΜΑΤΑ
        1εκ          |    0.028000      |  0.104000 |  0.048000   ΧΡΟΝΟΣ ΣΕ sec
        10εκ         |    0.207000      |  0.911000 |  0.314000  ΧΡΟΝΟΣ ΣΕ sec
        100εκ        |    1.744000      |  7.434000 |  3.052000  ΧΡΟΝΟΣ ΣΕ sec
        1δις         |    16.00100      |  74.77700 |  26.807000  ΧΡΟΝΟΣ ΣΕ sec -->ακραία βελτίωση

         */

    public static void main(String[] args) {

        long numSteps = 1000000000;
        int numThreads=8;
        double sum=0.0;
        Lock lock = new ReentrantLock();

        //ΦΤΙΑΧΝΩ ΤΑ ΝΗΜΑΤΑ
        MyThread threads[] = new MyThread[numThreads];

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
            threads[i] = new MyThread(start,stop,step,lock,sum);
            threads[i].start();
        }

        try {
            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sum =threads[0].getSharedSum();
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
    private long myStart, myStop;
    private double myStep;
    private Lock lock;
    private static double sharedSum = 0.0;



    public MyThread(long start, long stop, double step,Lock lock,double sum) {
        myStart = start;
        myStop = stop;
        myStep = step;
        this.lock = lock;
        sharedSum = sum;
    }

    public void run() {
        for (long i = myStart; i < myStop; ++i) {
            double x = ((double) i + 0.5) * myStep;
            lock.lock();
            try {
             sharedSum += 4.0 / (1.0 + x * x);
            } finally {
                lock.unlock();
            }

    }}

    public double getSharedSum() {
        return sharedSum;
    }
}


