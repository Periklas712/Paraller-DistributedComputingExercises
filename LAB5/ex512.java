public class ex512 {

    public static void main(String args[]) {
        int size = 100;
        int numThreads = Runtime.getRuntime().availableProcessors();

        double[] a = new double[size];
        double[] b = new double[size];
        double[] c = new double[size];
        for (int i = 0; i < size; i++) {
            a[i] = 0.0;
            b[i] = 1.0;
            c[i] = 0.5;
        }

        myThread threads[] = new myThread[numThreads];
        int block = size / numThreads;
        int start = 0;
        int stop = 0;

        for (int i = 0; i < numThreads; i++) {
            start = i * block;
            stop = i * block + block;
            if (i == numThreads - 1) stop = size;
            threads[i] = new myThread(a, b, c, start, stop);
            threads[i].start();
        }


        for (int i = 0; i < size; i++) {
            a[i] = b[i] + c[i];
        }


    for(int i = 0; i < size; i++)
        System.out.println(a[i]);
    }
}

        class myThread extends Thread {
            double[] a;
            double[] b;
            double[] c;
            int myStart;
            int myStop;

            myThread(double[] a, double[] b, double[] c, int myStart, int myStop) {
                this.a = a;
                this.b = b;
                this.c = c;
                this.myStart = myStart;
                this.myStop = myStop;
            }


            public void run() {
                for(int i = myStart; i < myStop; i++) {
                    a[i] = b[i] + c[i];
                }
            }
        }