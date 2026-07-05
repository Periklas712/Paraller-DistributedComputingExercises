//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ex1erg3syncGlobalCounterArray {

    static int end = 1000;
    static int[] array = new int[end];
    static int numThreads = 4;

    //anti gia to lock pou htan global metavliti tha ftiakso to global object kai tha xrhsimopoihso to lock tou sto nhma
    static Object lock_Object = new Object();

    public static void main(String[] args) {

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread();
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }
        check_array();
    }

    static void check_array() {
        int i, errors = 0;

        System.out.println("Checking...");

        for (i = 0; i < end; i++) {
            if (array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }


    static class CounterThread extends Thread {

        public CounterThread() {
        }

        public void run() {

            for (int i = 0; i < end; i++) {
                //edo tha valo to synchronized

                    for (int j = 0; j < i; j++)
                        synchronized (lock_Object) {
                            array[i]++;
                        }
            }
        }
    }
}