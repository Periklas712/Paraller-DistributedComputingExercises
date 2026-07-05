class Philosopher extends Thread {
  private int identity;
  private Fork left;
  private Fork right;
  private int scale;
  private int next;

  Philosopher(int id, int n, int s, Fork l, Fork r) {
    	identity = id; next = n; scale = s; left = l; right = r; 
  }

  public void run() {
     while (true) {
      //thinking
		  System.out.println(" Philosopher "+ identity + " is thinking");
        delay(scale);

        //θα εφαρμόσω την λύση που ο φιλόσοφος θα πάρει πρώτα πιρύνι με το μικροτςρο id και μετα με το μεγαλύτερο
        //δηλαδη πρωτα θα παρει το αριστερα και μετα το δεξια εκτοσ απο τον τελευταιο που ππρωτα θα παρει το δεξια και μετα το αρεστερα πηρουνι
        //αν το id του φιλοσοφου ειναι μικροτερο απο του επόμενου πρωτα θα παρει το αριστερα  πηρουνι και μετα το δεξιά
        if (identity<next){
          System.out.println(" Philosopher "+ identity+ " is trying to get fork " + identity);
          left.get();
          System.out.println(" Philosopher "+ identity+ " got fork " + identity + " and is trying to get fork " + next);
          right.get();
          System.out.println(" Philosofer "+ identity+ " got fork " + next);
          System.out.println(" Philosopher "+ identity + " is eating");
        }
        // αν ειναι ο τεταρτοσ φιλοσοφοσ πρώτα θα πάρει το δεξιά και μετά το αριστερό πιρούνι
        else{
          System.out.println(" Philosopher "+ identity+ " is trying to get fork " + identity);
          right.get();
          System.out.println(" Philosopher "+ identity+ " got fork " + identity + " and is trying to get fork " + next);
          left.get();
          System.out.println(" Philosofer "+ identity+ " got fork " + next);
          System.out.println(" Philosopher "+ identity + " is eating");
        }

        System.out.println(" Philosopher "+ identity + " is releasing left fork " + next);
        left.put();
        System.out.println(" Philosopher "+ identity + " is releasing fork " + identity);
        right.put();
     
     /*    //hungry
      System.out.println(" Philosopher "+ identity+ " is trying to get fork " + identity);
        right.get();
        //gotright chopstick
		  System.out.println(" Philosopher "+ identity+ " got fork " + identity + " and is trying to get fork " + next);
        left.get();
      System.out.println(" Philosopher "+ identity + " is eating");
        //eating
      System.out.println(" Philosopher "+ identity + " is releasing left fork " + next);
        //delay(scale);
        left.put();
		  System.out.println(" Philosopher "+ identity + " is releasing fork " + identity);
		  //delay(scale);
        right.put();
        */
     }    
  }

  public void delay(int scale) {
	try {
		 sleep((int)(Math.random()*scale));
	} catch (InterruptedException e) { }
  }
}
