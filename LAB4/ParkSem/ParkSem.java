import java.util.concurrent.Semaphore;

public class ParkSem {

	private int capacity;
	private int spaces;
	private int waitscale;
	private int inscale;
    private Semaphore mutex;
    private Semaphore parkFull ;
    private Semaphore parkEmpty ;
    
    public ParkSem(int c) {
       capacity = c;
       spaces = capacity;
       waitscale = 1000;
       inscale = 5;
       mutex = new Semaphore(1);
       parkFull = new Semaphore(0);
       parkEmpty= new Semaphore(capacity);
    }
       
	void arrive () {
		//Car arrival with random delay
        try {
            parkEmpty.acquire(); //ΠΕΡΙΜΕΝΕΙ ΓΙΑ ΕΛΕΘΕΥΡΗ ΘΕΣΗ
            mutex.acquire();
            Thread.sleep((int) (Math.random() * waitscale));
            System.out.println(Thread.currentThread().getName() + " arrival");
            //Car entering
            System.out.println(Thread.currentThread().getName() + " parking");
            //Decrement capacity
            spaces--;
            System.out.println("Number of free spaces: " + spaces);
            mutex.release();
            parkFull.release(); //ΣΗΜΑ ΟΤΙ ΠΑΡΚΑΡΕ
        }catch (InterruptedException e) { }
	}

    void depart () {
        //Car departure
        try {
            parkFull.acquire();
            mutex.acquire();
            System.out.println(Thread.currentThread().getName()+" departure");
            //Increment capacity
            spaces++;
            System.out.println("Number of free spaces: "+ spaces);
            mutex.release();
            parkEmpty.release(); //ΣΗΜΑ ΟΤΙ ΑΔΕΙΑΣΕ ΘΕΣΗ
        }catch (InterruptedException e) {}
    }            
           
    void park() {    
        try {
                Thread.sleep((int)(Math.random()*inscale));
            } catch (InterruptedException e) { }
    }
}
