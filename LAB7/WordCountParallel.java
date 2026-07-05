import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Handler;


//ΜΕΓΕΘΟΣ ΑΡΧΕΙΟΥ = 80 ΦΟΡΕΣ ΤΟ ΑΡΧΙΚΟ = 314 MB (330,272,180 bytes)

/*   ΑΚΟΛΟΥΘΙΑΚΑ |    1    |     2     |     4     |     8     |    16      ΝΗΜΑΤΑ 
       18.977    |  4.159  |   3.635   |   2.607   |   3.212   |    4.163   ΧΡΟΝΟΣ ΣΕ ΔΕΥΤΕΡΟΛΕΠΤΑ
 
*/

public class WordCountParallel {
     public static void main(String args[]) throws FileNotFoundException, IOException {
        
        int numThreads=4;
        
        String fileString = new String(Files.readAllBytes(Paths.get("sixtimesbibe.txt")));//, StandardCharsets.UTF_8);
        String[] words = fileString.split("[ \n\t\r.,;:!?(){}]");    
        
        ConcurrentHashMap<String,Integer> map = new ConcurrentHashMap<>();
        MyThread[] threads = new MyThread[numThreads];

        long startTime = System.nanoTime();

        int blockSize=words.length/numThreads;
        int start=0;
        int stop=0;

        for (int i =0;i<numThreads;i++){
            start=blockSize*i;
            stop = (i == numThreads - 1) ? words.length : start + blockSize;
            threads[i]= new MyThread(words,start,stop,map);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime(); 
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("Χρόνος εκτέλεσης: %.3f δευτερόλεπτα%n", durationInSeconds);

        // for (String name: map.keySet()) {
		// 	String key = name.toString();
		// 	String value = map.get(name).toString();
    	// 	System.out.println(key + "\t " + value);
        // }

}}

class MyThread extends Thread{
    private String[] words;
    private int myStart;
    private int myStop;
    private ConcurrentHashMap<String,Integer> map;

    public MyThread(String[] words, int start , int stop,ConcurrentHashMap<String,Integer> map){
        this.words=words;
        this.myStart=start;
        this.myStop=stop;
        this.map=map;
    }

    public void run(){
        for (int i=myStart;i<myStop;i++){
            String word = words[i].toLowerCase();
                if (!word.isEmpty()) {
                    //ΜΕΘΟΔΟΣ ΤΗΣ MAP ΓΙΑ ΣΥΓΩΝΕΥΣΗ ΤΙΜΩΝ ΧΩΡΙΣ RACE CONDITION
                    //ΑΝ Η ΛΕΞΗ ΕΙΝΑΙ ΑΔΕΙΑ ΒΑΖΕΙ 1 ΑΛΛΙΩΣ ΠΡΟΣΘΕΤΕΙ 1 ΣΤΟ ΣΥΝΟΛΟ
                    map.merge(word, 1, Integer::sum); 
                }
        }
    }
}





