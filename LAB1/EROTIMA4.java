
public class EROT4 {

	//ΕΡΩΤΗΣΗ 4 ΕΡΓΑΣΙΑ 1
	public static void main(String[] args) {
		
		System.out.println("Number | Thread id | Result");
		
		int numThreads = 10;
		Thread[] threads = new Thread[numThreads];
		
		for (int i=0;i<numThreads;i++) {
			threads[i]=new myThread(i+1);
			threads[i].start();
//			try {
//				threads[i].join();
//			} catch (InterruptedException e) {
//		}
		}

		for (int i=0;i<numThreads;i++) {
			
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	//ΒΛΕΠΩ ΟΤΙ ΟΙ ΕΚΤΥΠΩΣΕΙΣ ΓΙΝΟΝΤΙΑ ΤΑΥΤΟΧΡΟΝΑ ΚΑΙ ΜΠΕΡΔΕΜΕΝΑ ΔΗΛΑΔΗ ΑΝΑΜΕΣΑ ΣΕ ΚΑΘΕ ΕΚΤΥΠΩΣΗ ΕΝΟΣ ΝΗΜΑΤΟΣ ΠΑΡΕΜΒΑΛΕΤΑΙ Η ΕΚΤΥΠΩΣΗ 
	//ΕΝΟΣ ΑΛΛΟΥ ΝΗΜΑΤΟΣ. ΜΕΡΙΚΕΣ ΦΟΡΕΣ ΜΠΟΡΕΙ ΚΑΠΟΙΟ ΝΗΜΑ ΝΑ ΕΚΤΥΠΩΣΕΙ ΜΕ ΣΕΙΡΑ ΤΑ ΣΤΟΙΧΕΙΑ ΤΟΥ ΑΛΛΑ ΤΙς ΠΕΡΡΙΣΟΤΕΡΕΣ ΦΟΡΕΣ ΠΑΡΕΜΒΑΛΕΤΑΙ ΚΑΠΟΙΟ ΑΛΛΟ ΝΗΜΑ 
	
}

class myThread extends Thread{
	
	private int id;

	public myThread(int id) {
		this.id=id;
	}
	
	@Override
	public void run() {
		for (int i=0;i<=20;i++) {
			
			 System.out.println(i + " * " + id + " = " + (i * id));
		}
		
	}
	
	
	
}
