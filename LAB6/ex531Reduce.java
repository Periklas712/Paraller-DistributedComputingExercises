import java.util.LinkedList;

//ΔΟΚΙΜΑΖΩ ΣΕ ΣΥΣΤΗΜΑ 8 CORES ΜΕ 16 LOGICAL PROCESSORS

/*         Vector size |  Ακολουθιακά  |  2  |  4  |  8  |  16  ΝΗΜΑΤΑ
                23     |      195      | 142 | 138 | 198 | 372  ΧΡΟΝΟΣ ΣΕ MS
                24     |      349      | 242 | 204 | 252 | 401  ΧΡΟΝΟΣ ΣΕ MS
                25     |      708      | 417 | 313 | 324 | 466  ΧΡΟΝΟΣ ΣΕ MS
                26     |     1311      | 798 | 561 | 500 | 488  ΧΡΟΝΟΣ ΣΕ MS
*/

class ex531Reduce {

    static int size = 30;
    static int numThreads = 16;
    static LinkedList<String>[] outputs = new LinkedList[numThreads];

    public static void main(String[] args) {

        int iterations = (int) Math.pow(2, size);

        int block = iterations / numThreads;
        int from = 0;
        int to = 0;
        Thread[] threads = new Thread[numThreads]; //ΠΙΝΑΚΑΣ ΜΕ ΝΗΜΑΤΑ

        for (int i = 0; i < numThreads; i++) {
            outputs[i] = new LinkedList<>();
        }

        long start = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            from = i * block;
            to = i * block + block;
            if (i == (numThreads - 1)) to = iterations;
            threads[i] = new Thread(new MyThread(from, to, i));
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }

        long elapsedTimeMillis = System.currentTimeMillis() - start;

        LinkedList<String> output = new LinkedList<>();
        for (int i = 0; i < numThreads; i++) {
            output.addFirst(String.valueOf(outputs[i]));
        }

//      System.out.println(output);
        System.out.println("All done\n");
        System.out.println("time in ms = " + elapsedTimeMillis);

    }

    static class MyThread implements Runnable {
        int myStart;
        int myStop;
        int threadId;

        public MyThread(int myStart, int myStop, int threadId) {
            this.myStart = myStart;
            this.myStop = myStop;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            for (int i = myStart; i < myStop; i++)
                check_circuit(i, threadId);

        }
    }

    static void check_circuit(int z, int threadId) {

        boolean[] v = new boolean[size];  /* Each element is a bit of z */

        for (int i = size - 1; i >= 0; i--)
            v[i] = (z & (1 << i)) != 0;


        boolean value =
                (v[0] || v[1])
                        && (!v[1] || !v[3])
                        && (v[2] || v[3])
                        && (!v[3] || !v[4])
                        && (v[4] || !v[5])
                        && (v[5] || !v[6])
                        && (v[5] || v[6])
                        && (v[6] || !v[15])
                        && (v[7] || !v[8])
                        && (!v[7] || !v[13])
                        && (v[8] || v[9])
                        && (v[8] || !v[9])
                        && (!v[9] || !v[10])
                        && (v[9] || v[11])
                        && (v[10] || v[11])
                        && (v[12] || v[13])
                        && (v[13] || !v[14])
                        && (v[14] || v[15])
                        && (v[14] || v[16])
                        && (v[17] || v[1])
                        && (v[18] || !v[0])
                        && (v[19] || v[1])
                        && (v[19] || !v[18])
                        && (!v[19] || !v[9])
                        && (v[0] || v[17])
                        && (!v[1] || v[20])
                        && (!v[21] || v[20])
                        && (!v[22] || v[20])
                        && (!v[21] || !v[20])
                        && (v[22] || !v[20]);


        if (value) {
            saveResult(v, z, threadId);
        }
    }

    static void saveResult(boolean[] v, int z, int threadId) {

        String result = null;
        result = String.valueOf(z);

        for (int i = 0; i < size; i++)
            if (v[i]) result += " 1";
            else result += " 0";

        outputs[threadId].add("\n" + result);
    }

}



