import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
  ΑΠΕΙΚΟΝΙΣΗ | ΜΕΓΕΘΟΣ  |  ΑΚΟΛΟΥΘΙΑΚΑ   |    2    |    4    |    8    |   12   |    16
	 ΣΤΑΤΙΚΗ |  1 ΔΙΣ   |     9108       |   7428  |   6848  |  6528   |  6578  |   6679  ΧΡΟΝΟΣ ΣΕ ms
	ΚΥΚΛΙΚΗ  |  1 ΔΙΣ   |       -        |    -    | 122211  |  68703  |  68033 |   46162 ΧΡΟΝΟΣ ΣΕ ms
	ΔΥΝΑΜΙΚΗ |  1 ΔΙΣ   |      9323      |   6408  |  6124   |  4118   |  3503  |   4051
*/

public class SieveOfEratosthenesDynamic {

	static int size = 1000000000; 
	static int numThreads = 16;
	static int nextTask = -1;
	static Lock lock = new ReentrantLock();

	public static void main(String[] args) {

		boolean[]  prime = new boolean[size + 1];

		for (int i = 2; i <= size; i++)
			prime[i] = true;

		
		int limit = (int) Math.sqrt(size);
		boolean[] isPrimeSmall = new boolean[limit + 1];
		for (int i = 2; i <= limit; i++)
			isPrimeSmall[i] = true;

		for (int p = 2; p * p <= limit; p++) {
			if (isPrimeSmall[p]) {
				for (int i =p * p; i <= limit; i+= p) {
					isPrimeSmall[i] = false;
				}
			}
		}

		int count = 0;
		for (int i = 2; i <= limit; i++) {
			if (isPrimeSmall[i]) count++;
		}

		int[] smallPrimes = new int[count];
		int index = 0;
		for (int i = 2; i <= limit; i++) {
			if (isPrimeSmall[i]) smallPrimes[index++] = i;
		}

		long start = System.currentTimeMillis();

		Thread[] threads = new Thread[numThreads];
		for (int i = 0; i < numThreads; i++) {
			threads[i] = new Thread(new Worker(i,prime,smallPrimes));
			threads[i].start();
		}

		for (int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		long elapsedTimeMillis = System.currentTimeMillis() - start;

		int totalPrimes = 0;
		for (int i = 2; i <= size; i++) {
			if (prime[i]) totalPrimes++;
		}

		System.out.println("number of primes " + totalPrimes);
		System.out.println("time in ms = " + elapsedTimeMillis);
	}

	
	static class Worker implements Runnable {
		private int id;
		private boolean[] prime;
		private int[] smallPrimes;

		Worker(int id,boolean[] prime,int[] smallPrimes) {
			this.id = id;
			this.prime=prime;
			this.smallPrimes=smallPrimes;
		}

		public void run() {
			int task;
			while ((task = getNextTask()) >= 0) { //AN ΥΠΑΡΧΕΙ ΕΡΓΑΣΙΑ ΣΗΜΑΙΝΕΙ ΤΠΥ ΑΝΑΤΕΘΗΚΕ ΕΝΑ ΜΙΚΡΟΣ ΠΡΩΤΟΣ ΑΡΟΘΜΟΣ
				int p = smallPrimes[task];
				for (long i = (long) p * p; i <= size; i += p) {
					prime[(int) i] = false; //ΞΕΚΙΝΩΝΤΑΣ ΑΠΟ ΤΟ Ρ ΤΕΤΡΑΓΩΝΟ ΑΚΥΡΩΝΕΙ ΟΛΑ ΤΑ ΠΟΛΛΑΠΛΑΣΙΑ ΤΟΥ 
				}
			}
		}

		private int getNextTask() {
			lock.lock();
			try {
				if (++nextTask < smallPrimes.length) {
					return nextTask;
				} else {
					return -1;
				}
			} finally {
				lock.unlock();
			}
		}
	}
}
