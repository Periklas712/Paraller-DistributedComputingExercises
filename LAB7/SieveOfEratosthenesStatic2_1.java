
/*
    ΣΤΑΤΙΚΗ 	 |  ΑΚΟΛΟΥΘΙΑΚΑ   |    2    |    4    |    8    |   12   |    16
 ΜΕΓΕΘΟΣ = 1 ΔΙΣ |     9108       |   7428  |   6848  |  6528   |  6578  |   6679  ΧΡΟΝΟΣ ΣΕ ms
 
*/
class SieveOfEratosthenesStatic
{
	public static void main(String[] args)
	{  
		int numThreads=16;
		int size = 1000000000;

		MyThread[] threads = new MyThread[numThreads];
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

		int block = size/numThreads;
		
		for (int i =0 ;i<numThreads;i++){
			int start=i*block;
			int stop = (i == numThreads - 1) ? size : start+block;
			threads[i] = new MyThread(start, stop,smallPrimeList, prime);
			threads[i].start();
		}

		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		int finalcount = 0;
		for (int i = 2; i <= size; i++) {
			if (prime[i]==true) {
				finalcount++;
			}
		}
		
               
		// get current time and calculate elapsed time
		long elapsedTimeMillis = System.currentTimeMillis()-startTime;
				
		System.out.println("number of primes "+finalcount); 
		System.out.println("time in ms = "+ elapsedTimeMillis);
	}

}

class MyThread extends Thread{
	private int myStart;
	private int myStop;
	private int[] smallPrimeList;
	private boolean[] prime ;

	public MyThread(int myStart,int myStop,int[] smallPrimeList,boolean[] prime){
		this.myStart=Math.max(myStart,2);
		this.myStop=myStop;
		this.smallPrimeList=smallPrimeList;
		this.prime=prime;
	}

	public void run(){
		for (int p : smallPrimeList) {
			//ΥΠΟΛΟΓΙΧΩ ΤΟ ΠΡΩΤΟ ΠΟΛΛΑΠΛΑΣΙΟ 
			int firstMultiple = Math.max(p * p, ((myStart + p - 1) / p) * p);
			//ΞΕΚΙΝΑΩ ΝΑ ΣΒΗΝΩ ΑΠΟ ΤΟ ΠΡΩΤΟ ΠΟΛΛΑΠΛΑΣΙΟ ΜΕΧΡΙ ΤΟ ΤΕΛΟΣ
			for (int i = firstMultiple; i < myStop; i += p) {
				prime[i] = false;
			}
		}
		
	}
}
