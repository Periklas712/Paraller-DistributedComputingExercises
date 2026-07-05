public class ex1erg3syncMainCounterArray {

//        static int end = 1000;
//        static int[] array = new int[end];
//        static int numThreads = 4;

    public static void main(String[] args) {


        //anti gia static global variables tha tis dhmiourghso stin main kai tha tis peraso sta nhmata orismata
        int end =1000;
        int[] array = new int[end];
        int numThreads = 4;

        //tha ftiakso edo to object kai tha to peraso os moirazomeni metavliti se kathe nhma
        Object lock_Object = new Object();

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(end,array,lock_Object);
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
        Object lock_Object;

        public CounterThread(int End, int[] Array,Object Alock_Object) {
            this.end = End;
            this.array = Array;
            this.lock_Object = Alock_Object;
        }

        public void run() {

            for (int i = 0; i < end; i++) {

                    for (int j = 0; j < i; j++)
                        synchronized (lock_Object) {
                            array[i]++;
                        }

            }
        }
    }
}