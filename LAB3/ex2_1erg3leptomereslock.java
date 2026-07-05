import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ex2_1erg3leptomereslock{

//    static int end = 1000;
//    static int[] array = new int[end];
//    static int numThreads = 4;

    public static void main(String[] args) {

        //tha ftiakso ena antikeimeno pou tha exei to shared data
        int numThreads=4;

        CounterThread threads[] = new CounterThread[numThreads];
        SharedData data = new SharedData(numThreads);

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(data);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }
        check_array(data,numThreads);
    }

    static void check_array(SharedData data,int numThreads) {
        int i, errors = 0;

        System.out.println("Checking...");

        for (i = 0; i < data.end; i++) {
            if (data.array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, data.array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }
}

class SharedData{
    //auto edo to antikeimeno einai pou moirazetai
    int end;
    int[] array;

    //ftiaxno ena array list apo locks
    ArrayList<Lock> locks = new ArrayList<>();

    public SharedData(int numThreads) {
        this.end = 1000;
        this.array = new int[end];
        for (int i = 0; i < end; i++) {
            //prosthesto sto array list apo locks tosa kleidomata osa kai o pinakas array
            locks.add(new ReentrantLock());
        }
    }

}


class CounterThread extends Thread {
    SharedData data;

    public CounterThread(SharedData data) {
        this.data = data;
    }

    public void run() {

        for (int i = 0; i < data.end; i++) {
            //edo kleidono kathe thesi tou pinaka me kathe ena ksexoristo kleidoma
            //etsi epitygxano leptomeres kleidoma
            //αμα εβαζα το lock μεσα στο δευτερο for loop θα κλειδωνα πολλεσ φορεσ ( j ) το ιδιο στοιχείο array[i]
            //παλι τα ίδια αποτελέσματα θα είχα αλλά με τα πολλαπλα κλειδώματα θα ειχαμε ισως προβληματα performance
            data.locks.get(i).lock();
            try {
            for (int j = 0; j < i; j++)
                data.array[i]++;
            } finally {
                data.locks.get(i).unlock();
            }
        }
    }
}


