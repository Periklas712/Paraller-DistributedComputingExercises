
public class ParkMon {

	private int capacity;
	private int spaces;
	private int waitscale;
	private int inscale;
    private boolean parkEmpty;
    private boolean parkFull;

    public ParkMon(int c) {
       capacity = c;
       spaces = capacity;
       waitscale = 500;
       inscale = 500;
        parkEmpty = true;
        parkFull = (spaces == 0);
    }

        synchronized  void  arrive() {
		//Car arrival with random delay
        try {
            while(parkFull) {
                System.out.println("Park Full. " + "Car = "+Thread.currentThread().getName() + " waits.");
                wait();
            }
            Thread.sleep((int) (Math.random() * waitscale));
            System.out.println("Car = " +Thread.currentThread().getName() + " arrival");
            //Car entering
            System.out.println("Car = "+Thread.currentThread().getName() + " parking");
            //Decrement capacity
            spaces--;
            parkEmpty = (spaces == capacity);
            parkFull = (spaces == 0);
            System.out.println("Number of free spaces: " + spaces);
            notifyAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

	}

    synchronized void depart () {
        //Car departure
        System.out.println("Car = "+ Thread.currentThread().getName() + " departure");
        //Increment capacity
        spaces++;
        parkEmpty = (spaces == capacity);
        parkFull = (spaces == 0);
        System.out.println("Number of free spaces: " + spaces);
        notifyAll();
    }
           
    void park() {    
        try {
                Thread.sleep((int)(Math.random()*inscale));
            } catch (InterruptedException e) { }
    }
}
