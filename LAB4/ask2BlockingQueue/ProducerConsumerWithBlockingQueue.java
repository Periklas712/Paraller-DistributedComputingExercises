import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerWithBlockingQueue
{
    public static void main(String[] args)
    {
        //ΦΤΙΑΧΝΩ ΕΝΑ BLOCKING QUEUE ΜΕ ΑΚΕΡΑΙΟΥΣ,ΚΑΙ ΟΠΩΣ ΣΤΟΝ ΚΩΔΙΚΑ ΠΟΥ ΔΩΣΑΤΕ Ο ΠΑΡΑΓΩΓΟΣ ΘΑ ΚΑΝΕΙ 20 ΕΠΑΝΑΛΗΨΕΙΣ
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        int noIterations = 20;
        int producerDelay = 1;
        int consumerDelay = 1;

        //ΔΗΜΙΟΥΡΓΩ ΤΟΝ ΠΑΤΑΓΩΓΟ ΚΑΙ ΤΟΝ ΚΑΤΑΝΑΛΩΤΗ
        Producer producer = new Producer(queue, noIterations, producerDelay);
        Consumer consumer = new Consumer(queue, consumerDelay);

        //ΞΕΚΙΝΑΩ ΤΑ ΝΗΜΑΤΑ
        new Thread(producer).start();
        new Thread(consumer).start();


    }
}
