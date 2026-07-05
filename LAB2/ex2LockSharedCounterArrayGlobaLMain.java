
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ex2LockSharedCounterArrayGlobaLMain {

//        static int end = 1000;
//        static int[] array = new int[end];
//        static int numThreads = 4;

    public static void main(String[] args) {

        //efoson den xrisompoio amoivaio apokleismpo tha exo lathos
        //anti gia static global variables tha tis dhmiourghso stin main kai tha tis peraso sta nhmata orismata
        int end =1000;
        int[] array = new int[end];
        int numThreads = 4;
        //efoson dhmioyrgo tis metavlites sthn main tha prepei kai to lock na einai sthn main kai na peraso orisma sta nhmata
        //oste ola ta nhmata na exoun koino lock
        Lock lock = new ReentrantLock();

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(end,array,lock);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {}
        }
        check_array (end,array,numThreads);
    }

    static void check_array (int end,int[] array,int numThreads)  {
        int i, errors = 0;

        System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
            if (array[i] !=numThreads* i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, array[i], numThreads*i);
            }
        }
        System.out.println (errors+" errors.");
    }


    static class CounterThread extends Thread {

        int end;
        int[] array;
        Lock lock;

        public CounterThread(int End, int[] Array,Lock lock) {
            this.end = End;
            this.array = Array;
            this.lock = lock;
        }

        public void run() {

            for (int i = 0; i < end; i++) {
               lock.lock();
               try{
                for (int j = 0; j < i; j++)
                    array[i]++;

                }finally{ lock.unlock();}
                
        }
    }
}
}






