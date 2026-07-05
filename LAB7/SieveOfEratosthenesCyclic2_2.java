
/*
  ΑΠΕΙΚΟΝΙΣΗ | ΜΕΓΕΘΟΣ  |  ΑΚΟΛΟΥΘΙΑΚΑ   |    2    |    4    |    8    |   12   |    16
	 ΣΤΑΤΙΚΗ |  1 ΔΙΣ   |     9108       |   7428  |   6848  |  6528   |  6578  |   6679  ΧΡΟΝΟΣ ΣΕ ms
	ΚΥΚΛΙΚΗ  |  1 ΔΙΣ   |       -        |    -    | 122211  |  68703  |  68033 |   46162 ΧΡΟΝΟΣ ΣΕ ms
*/
class SieveOfEratosthenesCyclic
{
	public static void main(String[] args)
	{  
		int numThreads=16;
		int size = 1000000000;

		MyCyclicThread[] threads = new MyCyclicThread[numThreads];
		boolean[] prime = new boolean[size+1];

		for(int i = 2; i <= size; i++)
			prime[i] = true; 
			
		//ΥΠΟΛΟΓΙΖΩ ΤΟΥΣ ΜΙΚΡΟΥΣ ΠΡΩΤΟΥΣ ΑΡΙΘΜΟΥΣ ΜΕΧΡΙ ΤΗΝ ΡΙΖΑ SIZE 
		//for (int p = 2; p*p <= size; p++)
		int limit = (int)Math.sqrt(size) + 1;
		boolean[] smallPrimes = new boolean[limit+1];
		for (int i = 2; i <= limit; i++)
			smallPrimes[i] = true;

		for (int p = 2; p * p <= limit; p++) {
			if (smallPrimes[p]) {
				for (int i= p * p;i<= limit;i+=p)
				smallPrimes[i] = false;
			}
		}
		int count = 0;
		for (int i = 2; i <= limit; i++) {
			if (smallPrimes[i]==true) {
			count++;
			}
		}

		int[] smallPrimeList = new int[count];
		int index = 0;
		for (int i = 2; i <= limit; i++) {
    		if (smallPrimes[i]) {
        		smallPrimeList[index++] = i;
    		}
		}

		// get current time 
		long startTime = System.currentTimeMillis();

		for (int i =0 ;i<numThreads;i++){
			threads[i] = new MyCyclicThread(i,numThreads,smallPrimeList,prime,size);
			threads[i].start();
		}

		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// get current time and calculate elapsed time
		long elapsedTimeMillis = System.currentTimeMillis()-startTime;

		int finalcount = 0;
		for (int i = 2; i <= size; i++) {
			if (prime[i]==true) {
				finalcount++;
			}
		}
		
		System.out.println("number of primes "+finalcount); 
		System.out.println("time in ms = "+ elapsedTimeMillis);
	}

}

class MyCyclicThread extends Thread{
	private int threadId;
	private int numThreads;
	private int[] smallPrimeList;
	private boolean[] prime ;
	private int size;

	public MyCyclicThread(int threadId,int numThreads,int[] smallPrimeList,boolean[] prime,int size){
		this.threadId=threadId;
		this.numThreads=numThreads;
		this.smallPrimeList=smallPrimeList;
		this.prime=prime;
		this.size=size;
	}

	public void run() {
		for(int i =threadId;i<size;i+=numThreads){
			//ΕΛΕΓΧΩ ΑΝ Ο ΑΡΙΘΜΟΣ Ι ΠΟΥ ΕΧΕΙ ΑΝΑΤΕΘΕΙ ΣΤΟ ΝΗΜΑ ΕΙΝΑΙ ΕΙΝΑΙ ΠΡΩΤΟΣ Η ΟΧΙ
			for (int p : smallPrimeList) {
                if (p * p > i) break; //ΑΝ ΚΑΝΕΝΑΣ ΠΡΩΤΟΣ ΔΕΝ ΔΙΑΙΡΕΙ ΜΕΧΡΙ ΡΙΖΑ Ι ΔΕΝ ΔΙΑΙΡΕΙ ΤΟΝ Ι ΤΟΤΕ Ο Ι ΕΙΝΑΙ ΠΡΩΤΟΣ 
                if (i % p == 0) { //ΑΝ Ο ΑΡΙΘΜΟΣ Ι ΔΙΑΡΕΙΤΑΙ ΜΕ ΚΑΠΟΙΟΝ ΜΙΚΡΟ ΠΡΩΤΟ Ι ΤΟΤΕ Ι ΔΕΝ ΕΙΝΑΙ ΠΡΩΤΟΣ 
                    prime[i] = false;
                    break; 
			}
		}
	}
	
}
}
