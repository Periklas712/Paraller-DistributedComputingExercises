
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private BlockingQueue<Integer> queue;
    private int scale;

    public Consumer(BlockingQueue<Integer> q,int s){
        this.queue=q;
        this.scale=s;

    }

    //Ο ΚΑΤΑΝΑΛΩΤΗΣ ΘΑ ΤΡΕΞΕΙ ΜΕΧΡΙ ΝΑ ΑΔΕΙΑΣΕΙ Η ΟΥΡΑ
    public void run(){
        try{
            Integer value=null;
            while(true){
                Thread.sleep((int)(Math.random() * scale));
                value=queue.take(); //ΠΑΙΡΝΩ ΤΟ ΣΤΟΙΧΕΙΟ
                System.out.println("Consumed : "+value);
            }
        } catch(InterruptedException e) {}
    }


}

