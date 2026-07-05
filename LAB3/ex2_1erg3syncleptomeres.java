import java.util.ArrayList;

public class ex1erg3syncleptomeres{

//    static int end = 1000;
//    static int[] array = new int[end];
//    static int numThreads = 4;

    public static void main(String[] args) {

        int numThreads=4;

        CounterThread threads[] = new CounterThread[numThreads];
        //tha ftiakso ena antikeimeno pou tha exei to shared data
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
    //ftiakxo edo ton pinaka me ta  object gia to synchronized
    ArrayList<Object> objects_locks = new ArrayList<>();



    public SharedData(int numThreads) {
        this.end = 1000;
        this.array = new int[end];
        //προσθετω στον πινακα τοσα object οσα και το μεγεθοσ του array
        for (int i = 0; i < end; i++) {
            objects_locks.add(new Object());
        }
    }

    //eftiaksa edo thn methodo pou auksanei to pinaka anti na to balo mesa sto nhma
    public void increase_array(int i){
        //εδω επιτυγχανω το λεπτομερες κλειδωμα αφου για καθε γραμμη του array που θα αυξηθει παιρνω
        // την αντιστοιχο object και χρησιμοποιω το κλειδωμα του
        synchronized (objects_locks.get(i)) {
            array[i]++;
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
            for (int j = 0; j < i; j++)
                data.increase_array(i);
            //an den evaza methodo sto moirazomeno antikeimeno tha htan kapos etsi:
//                synchronized (data.lock_obj) {
//                    data.array[i]++;
//                }

        }
    }
}


