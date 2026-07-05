import java.lang.Math;
import java.util.LinkedList;

//ΔΟΚΙΜΑΖΩ ΣΕ ΣΥΣΤΗΜΑ 8 CORES ΜΕ 16 LOGICAL PROCESSORS
class SimpleSatOutput {

    //ΤΙΣ ΔΗΛΩΝΩ ΩΣ GLOBAL ΜΕΤΑΒΛΗΤΕΣ ΓΙΑ ΝΑ ΜΗΝ ΤΗΝ ΠΕΡΝΑΩ ΣΕ ΚΑΘΕ ΝΗΜΑ
    static int size = 26;
    static LinkedList<String> output = new LinkedList<String>();
    static int numThreads = 16;

    public static void main(String[] args) {

        /* Vector size |  Ακολουθιακά  |  1   |  2  |  4  |  8  |  16  ΝΗΜΑΤΑ
                23     |      238      | 215  | 137 | 129 | 209 | 406 ΧΡΟΝΟΣ ΣΕ MS
                24     |      444      | 419  | 254 | 192 | 256 | 254 ΧΡΟΝΟΣ ΣΕ MS
                25     |      827      | 833  | 503 | 418 | 426 | 614 ΧΡΟΝΟΣ ΣΕ MS
                26     |     1613      | 1780 |1180 | 795 | 734 | 673 ΧΡΟΝΟΣ ΣΕ MS
         */
        // if (args.length != 1) {
        //     System.out.println("Usage: java SimpleSat <vector size>");
        //     System.exit(1);
        // }

        // try {
        //     size = Integer.parseInt(args[0]);
        // }
        // catch (NumberFormatException nfe) {
        //     System.out.println("Integer argument expected");
        //     System.exit(1);
        // }
        // if (size <= 0) {
        // 	System.out.println("size should be positive integer");
        // 	System.exit(1);
        // }

        int iterations = (int) Math.pow(2, size);

        //ΧΩΡΙΖΩ ΤΟ ΚΟΜΜΑΤΙ ΠΟΥ ΘΑ ΠΑΡΕΙ ΚΑΘΕ ΝΗΜΑ
        int block = iterations / numThreads;
        int from=0;
        int to=0;
        Thread[] threads = new Thread[numThreads]; //ΠΙΝΑΚΑΣ ΜΕ ΝΗΜΑΤΑ

        long start = System.currentTimeMillis();

        for (int i=0;i<numThreads;i++) {
            from=i*block;
            to=i*block+block;
            if (i == (numThreads - 1)) to = iterations;
            threads[i] = new Thread(new MyThread(from,to));
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        long elapsedTimeMillis = System.currentTimeMillis()-start;

//        System.out.println(output);
        System.out.println ("All done\n");
        System.out.println("time in ms = "+ elapsedTimeMillis);

    }

    static class MyThread implements Runnable {
        int myStart;
        int myStop;

        public  MyThread(int myStart, int myStop) {
            this.myStart = myStart;
            this.myStop = myStop;
        }

        @Override
        public void run() {
            for (int i = myStart; i < myStop; i++)
                check_circuit (i);

        }
    }

    static synchronized void check_circuit (int z) {

        boolean[] v = new boolean[size];  /* Each element is a bit of z */

        for (int i = size-1; i >= 0; i--)
            v[i] = (z & (1 << i)) != 0;


        boolean value =
                (  v[0]  ||  v[1]  )
                        && ( !v[1]  || !v[3]  )
                        && (  v[2]  ||  v[3]  )
                        && ( !v[3]  || !v[4]  )
                        && (  v[4]  || !v[5]  )
                        && (  v[5]  || !v[6]  )
                        && (  v[5]  ||  v[6]  )
                        && (  v[6]  || !v[15] )
                        && (  v[7]  || !v[8]  )
                        && ( !v[7]  || !v[13] )
                        && (  v[8]  ||  v[9]  )
                        && (  v[8]  || !v[9]  )
                        && ( !v[9]  || !v[10] )
                        && (  v[9]  ||  v[11] )
                        && (  v[10] ||  v[11] )
                        && (  v[12] ||  v[13] )
                        && (  v[13] || !v[14] )
                        && (  v[14] ||  v[15] )
                        && (  v[14] ||  v[16] )
                        && (  v[17] ||  v[1]  )
                        && (  v[18] || !v[0]  )
                        && (  v[19] ||  v[1]  )
                        && (  v[19] || !v[18] )
                        && ( !v[19] || !v[9]  )
                        && (  v[0]  ||  v[17] )
                        && ( !v[1]  ||  v[20] )
                        && ( !v[21] ||  v[20] )
                        && ( !v[22] ||  v[20] )
                        && ( !v[21] || !v[20] )
                        && (  v[22] || !v[20] );


        if (value) {
            saveResult(v, z);
        }
    }

    //ΕΔΩ ΚΑΝΩ synchronized ΤΗΝ ΜΕΘΟΔΟ ΕΠΕΙΔΗ ΤΑ ΝΗΜΑΤΑ ΜΠΟΡΕΙ ΝΑ ΓΡΑΨΟΥΝ ΤΑΥΤΟΧΡΟΝΑ ΣΤΟ ΤΕΛΟΣ ΤΗΣ ΛΙΣΤΑΣ ΚΑΙ ΜΠΟΡΕΙ ΝΑ ΥΠΑΡΞΕΙ ΠΡΟΒΛΗΜΑ
    //ΤΑΥΤΟΧΡΟΝΗΣ ΕΓΡΑΦΦΗΣ ΣΤΗΝ ΛΙΣΤΑ ΑΠΟ ΔΥΟ ΝΗΜΑΤΑ
    static  void saveResult (boolean[] v, int z) {

        String result = null;
        result = String.valueOf(z);

        for (int i=0; i< size; i++)
            if (v[i]) result += " 1";
            else result += " 0";

        //Just print result	for debugging
//        System.out.println(result);
        //Save result
        output.add("\n"+result);
    }

}
