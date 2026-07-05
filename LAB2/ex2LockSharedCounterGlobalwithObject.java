
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ex2LockSharedCounterGlobalwithObject {

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
    //ara kai to lock tha einai koino afou moirazetai
    Lock lock = new ReentrantLock();

    public SharedData(int numThreads) {
        this.end = 1000;
        this.array = new int[end];
    }

}


class CounterThread extends Thread {
    SharedData data;

    public CounterThread(SharedData data) {
        this.data = data;
    }

    public void run() {

        for (int i = 0; i < data.end; i++) {
            data.lock.lock();
            try {
                for (int j = 0; j < i; j++)
                    data.array[i]++;
            }finally {
                data.lock.unlock();
            }
        }
    }
}







