//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ex11SharedCounterArrayGlobalWhileMain {

     static int end = 10000;
//    static int counter = 0;
//    static int[] array = new int[end];
    static int numThreads = 4;

    public static void main(String[] args) {


        //tha ftiakso tis parapano metavlites sthn main kai tha tis peraso os orismata sta nhmata
        //to numThreads den einai moirazomeni metavliti opote den mas peirazei
        //edo to end einai moirazomeni metavliti alla efoson ginetai anagnosi ths mono den mas ephrazei

        //parathrhseis sta apotelesmata einai oti ta erros einai oso o arithmos tou pinaka dld to end dioti to counter o seira me thn opoia
        //ektelountai oi entoles array[counter]++ kai counter++ apo kathe nhma einai entelos tuxaiew

        int counter =0;
        int[] array = new int[end];

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(counter,array);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            }
            catch (InterruptedException e) {}
        }
        check_array (array);
    }

    static void check_array (int[] array)  {
        int i, errors = 0;

        System.out.println ("Checking...");

        for (i = 0; i < end; i++) {
            if (array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, array[i]);
            }
        }
        System.out.println (errors+" errors.");
    }


    static class CounterThread extends Thread {

        int[] array;
        int counter;

        public CounterThread(int aCounter,int[] aArray) {
            this.array = aArray;
            this.counter = aCounter;
        }

        public void run() {

            while (true) {
                if (counter >= end)
                    break;
                array[counter]++;
                counter++;
            }
        }
    }
}

