
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParkLockCond {

	private int capacity;
	private int spaces;
	private int waitscale;
	private int inscale;
    private Lock lock = new ReentrantLock();
    private Condition parkFull = lock.newCondition();
    private Condition parkEmpty = lock.newCondition();

    public ParkLockCond(int c) {
       capacity = c;
       spaces = capacity;
       waitscale = 1000;
       inscale = 5;
    }
       
	void arrive () {
		//Car arrival with random delay
        lock.lock();
        try {
            while (spaces==0) {
                System.out.println("Park Full");
                parkFull.await();
            }
            Thread.sleep((int) (Math.random() * waitscale));
            System.out.println(Thread.currentThread().getName() + " arrival");
            //Car entering
            System.out.println(Thread.currentThread().getName() + " parking");
            //Decrement capacity
            spaces--;
            System.out.println("Number of free spaces: " + spaces);
            if (spaces == capacity-1) parkEmpty.signalAll();
        }catch (InterruptedException e) { }
        finally {
            lock.unlock();
        }
	}

    void depart () {
        //Car departure
        lock.lock();
        try {
            while (spaces ==capacity) {
                System.out.println("Park Empty");
                parkEmpty.await();
            }
            System.out.println(Thread.currentThread().getName() + " departure");
            //Increment capacity
            spaces++;
            System.out.println("Number of free spaces: " + spaces);
            if (spaces == 1) parkFull.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {lock.unlock();}

    }            
           
    void park() {    
        try {
                Thread.sleep((int)(Math.random()*inscale));
            } catch (InterruptedException e) { }
    }
}
