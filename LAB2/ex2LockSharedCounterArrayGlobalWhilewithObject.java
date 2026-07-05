
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ex2LockSharedCounterArrayGlobalWhilewithObject {

//    static int end = 10000;
//    static int counter = 0;
//    static int[] array = new int[end];
//    static int numThreads = 4;

    public static void main(String[] args) {

        //tha ftiakso ena shared object vste na peraso tis times ton moirazomenon metavliton sta nhmata

        int numThreads = 4;
        SharedData data = new SharedData(); //ftiakxo to shared Object stin main

        CounterThread threads[] = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(data); //dimiourgo ta threads kai pernao to shared object
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
            }
        }
        check_array(data);
    }

    static void check_array(SharedData data) {
        int i, errors = 0;

        System.out.println("Checking...");

        for (i = 0; i < data.end; i++) {
            if (data.array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, data.array[i]);
            }
        }
        System.out.println(errors + " errors.");
    }
}

class SharedData {

    //to antikeimeno mou me tis moirazomenes metablites
    int end;
    int counter;
    int[] array;
    //to koino moirazomeno lock
    Lock lock = new ReentrantLock();

    public SharedData() {
        this.end=10000;
        this.counter=0;
        this.array=new int[end];
    }

}


class CounterThread extends Thread {

    SharedData data;

    public CounterThread(SharedData aData) {
        this.data=aData;
    }

    public void run() {

        while (true) {
            //prostateuo tis moirazomenes metavlites
            data.lock.lock();
            try {
                if (data.counter >= data.end)
                    break;
                data.array[data.counter]++;
                data.counter++;
            }finally {
                data.lock.unlock();
            }
        }
    }
}












