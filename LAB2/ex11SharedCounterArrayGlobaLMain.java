
public class ex11SharedCounterArrayGlobaLMain {

//        static int end = 1000;
//        static int[] array = new int[end];
//        static int numThreads = 4;

        public static void main(String[] args) {

            //efoson den xrisompoio amoivaio apokleismpo tha exo lathos
            //anti gia static global variables tha tis dhmiourghso stin main kai tha tis peraso sta nhmata orismata
            int end =1000;
            int[] array = new int[end];
            int numThreads = 4;

            CounterThread threads[] = new CounterThread[numThreads];

            for (int i = 0; i < numThreads; i++) {
                threads[i] = new CounterThread(end,array);
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
                if (array[i] != numThreads*i) {
                    errors++;
                    System.out.printf("%d: %d should be %d\n", i, array[i], numThreads*i);
                }
            }
            System.out.println (errors+" errors.");
        }


        static class CounterThread extends Thread {

            int end;
            int[] array;

            public CounterThread(int End, int[] Array) {
                this.end = End;
                this.array = Array;
            }

            public void run() {

                for (int i = 0; i < end; i++) {
                    for (int j = 0; j < i; j++)
                        array[i]++;
                }
            }
        }
    }
