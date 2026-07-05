import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pi_LocalSum_Lock {

    /*
    ΜΕΓΕΘΟΣ ΠΙΝΑΚΑ   |   Ακολουθιακά    |     2    |    4    |    8    |    16   ΝΗΜΑΤΑ
        1εκ          |    0.007000      |  0.0150  | 0.0130  | 0.0240  | 0.0270  ΧΡΟΝΟΣ ΣΕ sec
        10εκ         |    0.036000      |  0.0220  | 0.0170  | 0.0240  | 0.0340  ΧΡΟΝΟΣ ΣΕ sec
        100εκ        |    0.293000      |  0.1450  | 0.0860  | 0.0600  | 0.0580  ΧΡΟΝΟΣ ΣΕ sec
        1δις         |    2.366000      |  1.3630  | 0.7700  | 0.4570  | 0.2900  ΧΡΟΝΟΣ ΣΕ sec
                             ->       Σχεδόν 2 δευτερόλεπτα διαφορά         <-
         */

    public static void main(String[] args) {

        long numSteps = 1000000000;
        int numThreads=16;
        Lock lock = new ReentrantLock();
        SharedSumObj sharedSum = new SharedSumObj();

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
            threads[i] = new MyThread(start,stop,step,sharedSum);
            threads[i].start();
        }

        try {
            for (int i = 0; i < numThreads; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double pi = sharedSum.sum * step;

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
    private SharedSumObj sharedSumObj;
    private double myLocalSum=0.0;


    public MyThread(long start, long stop, double step,SharedSumObj sharedSumObj) {
        myStart = start;
        myStop = stop;
        myStep = step;
        this.sharedSumObj = sharedSumObj;
    }
    public void run() {

        for (long i=myStart; i < myStop; ++i) {
            double x = ((double)i+0.5)*myStep;
            myLocalSum += 4.0/(1.0+x*x);} //ΚΑΘΕ ΝΗΜΑ ΥΠΟΛΟΓΙΖΕΙ ΤΟ ΤΟΠΙΚΟ ΑΘΡΟΙΣΜΑ
            sharedSumObj.lock.lock();
            try{
                //ΚΑΘΕ ΝΗΜΑ ΑΦΟΤΟΥ ΕΧΕΙ ΥΠΟΛΟΓΙΣΕΙ ΤΟ ΤΟΠΙΚΟ ΤΟΥ ΑΘΡΟΙΣΜΑ, ΚΛΕΙΔΩΝΕΙ ΜΙΑ ΦΟΡΑ ΤΟ SUM KAI ΠΡΟΣΘΕΤΕΙ ΤΟ ΤΟΠΙΚΟ ΤΟΥ ΑΘΡΟΣΜΑ ΣΕ ΑΥΤΟ
                sharedSumObj.sum += myLocalSum;
            }
            finally {
               sharedSumObj.lock.unlock();}
        }
    }

    class SharedSumObj {
        public double sum=0.0;
        public Lock lock = new ReentrantLock();
    }

