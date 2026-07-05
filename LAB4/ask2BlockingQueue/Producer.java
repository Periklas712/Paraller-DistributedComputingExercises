
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Producer implements Runnable {

    private BlockingQueue<Integer> queue;
    private int reps;
    private int scale;

    public Producer(BlockingQueue<Integer> q, int r, int s) {
        this.queue = q;
        this.reps = r;
        this.scale = s;
    }

    //Ο ΠΑΡΑΓΩΓΟΣ ΘΑ ΚΑΝΕΙ 20 ΕΠΑΝΑΛΗΨΕΙΣ ΘΑ ΒΑΛΕΙ 20 ΣΤΟΧΕΙΑ
    public void run() {
        for (int i = 0; i < reps; i++) {
            Integer value = i;
            try {
                Thread.sleep((int)(Math.random() * scale));
                queue.put(value); //ΒΑΖΩ ΤΟ ΣΤΟΙΧΕΙΟ
                System.out.println("Produced : " + value);
            } catch (InterruptedException e) {}
        }
    }
}

