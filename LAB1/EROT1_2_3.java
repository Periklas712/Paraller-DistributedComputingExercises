


//Eρώτηση 1 . Αν δεν χρησιμοποιήσουμε δομή δεδομένων κατα την δημιουργια και αποθηκευση των νηματων τοτε στο ιδιο for loop
//  θα πρεπει να κανουμε start και join για καθε νημα . αυτο εχει ωσ αποτελεσμα καθε νεο νημα που δημιουργειται εκτελειται χωρίς να παρεμβάλεται η λειτουργία του
// και καποιο αλλο νημα να εκτελεστεί στην απο αυτό δηλαδή είναι ακολουθιακή η σειρά εκτέλεσης . αν αφαιρεσουμε το join τότε η σειρά εκτέλεσης των νημάτων 
// δεν θα ειναι ακολουθιακη καθως μπορει και η main να ολοκληρωθεί χωρίς να έχουν τελείωσει όλα τα νήματα 


public class EROT1_2_3 {

    public static void main(String[] args) {
    	
    	//ΕΡΩΤΗΣΗ 2 
    	System.out.println("From main create and start Threads A and B για ερώτημα 2");
    	ThreadΑ ta = new ThreadΑ(0);
    	ThreadB tb = new ThreadB(0);
    	ta.start();
    	tb.start();
    	try {
    		ta.join();
    		tb.join();
    	}catch(InterruptedException e){}
    	 
    	
    	System.out.println("Exit ερώτημα 2");
       
    	//ΕΡΩΤΗΣΗ 3
    	System.out.println("Ερώτημα 3");
    	int numThreads=10;
    	Thread[] threadsB = new Thread[numThreads];
    	Thread[] threadsA = new Thread[numThreads];
    	
    	for (int i=0;i<numThreads;i++) {
    		threadsA[i] = new ThreadΑ(i);
    		threadsB[i] = new ThreadB(i);
    		threadsA[i].start();
    		threadsB[i].start();
    	}
    	
    	for (int i=0;i<numThreads;i++) {
    		try {
				threadsA[i].join();
				threadsB[i].join();
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
    		
    	}
    
    	System.out.println("Exit ερώτημα 3");
    	
}
}

//ΝΗΜΑ Α ΤΥΠΩΝΕΙ ΤΥΧΑΙΟ ΑΡΙΘΜΟ ΜΕΤΑΞΥ 0 ΚΑΙ 1
class ThreadΑ extends Thread {
  
	private int id;
	
	public ThreadΑ(int id) {
		this.id=id;
	}
    
    public void run() {
    	
        System.out.println("From Thread A "+ id +" random number : "+ Math.random());  
    } 
}

//ΝΗΜΑ Β ΤΥΠΩΝΕΙ ΤΥΧΑΙΟ ΑΡΙΘΜΟ ΜΕΤΑΞΥ 1 ΚΑΙ 10 
class ThreadB extends Thread{
	private int id;
	
	public ThreadB(int id) {
		this.id=id;
	}
    
	public void run() {
		System.out.println("From Thread B "+ id+" random number : "+ 10*Math.random()); 
	}
	
	
}

