class ex511 {
    public static void main(String args[]) {
        int size = 10000;

        //ΠΑΙΡΝΩ ΤΟΝ ΑΡΙΘΜΟ ΤΩΝ ΠΥΡΗΝΩΝ ΠΟΥ ΕΧΩ
        int numThreads = Runtime.getRuntime().availableProcessors();

        double[][] a = new double[size][size];
        double[][] b = new double[size][size];
        double[][] c = new double[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                a[i][j] = 0.1;
                b[i][j] = 0.3;
                c[i][j] = 0.5;
            }

        //ΑΘΤΟ ΕΔΩ ΘΑ ΜΠΕΙ ΣΤΟ ΝΗΜΑ
//        for(int i = 0; i < size; i++)
//            for(int j = 0; j < size; j++)
//                a[i][j] = b[i][j] + c[i][j];

        //ΦΤΙΑΧΝΩ ΝΗΜΑΤΑ ΟΣΑ ΟΙ ΠΥΡΗΝΕΣ
        MyThread threads[] = new MyThread[numThreads];

        //ΚΑΤΑΝΕΜΩ ΤΟΥΣ ΠΙΝΑΚΕΣ ΣΕ ΚΑΘΕ ΝΗΜΑ
        int block = size / numThreads;
        int start = 0;
        int stop = 0;

        for (int i = 0; i < numThreads; i++) {
            start = i * block;
            stop = i * block + block;
            if (i == numThreads - 1) stop = size;
            threads[i] = new MyThread(a, b, c, start, stop);
            threads[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                System.out.print(a[i][j] + " ");
            System.out.println();
        }
    }
}

    class MyThread extends Thread{
        //δεν ειναι table κανονικα ειναι μια γραμμη
        private double[][] Atable;
        private double[][] Btable;
        private double[][] Ctable;
        private int myStart;
        private int myStop;

        public MyThread(double[][] atable, double[][] btable, double[][] ctable, int myStart, int myStop) {
            Atable = atable;
            Btable = btable;
            Ctable = ctable;
            this.myStart = myStart;
            this.myStop = myStop;
        }

        public void run() {
            //ξεκιναει το block του καθε νηματος
            //ο μεσα βροχος χειριζεται καθε στηλη
            for(int i = myStart; i < myStop; i++) {
                for (int j = 0; j < Atable[0].length; j++)
                Atable[i][j] = Btable[i][j] + Ctable[i][j];
            }

        }

    }


