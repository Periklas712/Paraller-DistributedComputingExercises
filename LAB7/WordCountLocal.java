import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//ΛΥΣΗ ΜΕ ΤΟΠΙΚΑ HASHMAP ΚΑΙ REDUCTION ΣΕ ΕΝΑ ΤΕΛΙΚΟ ConcurrentHashMap
//ΜΕΓΕΘΟΣ ΑΡΧΕΙΟΥ = 80 ΦΟΡΕΣ ΤΟ ΑΡΧΙΚΟ = 314 MB (330,272,180 bytes)

/*   ΑΚΟΛΟΥΘΙΑΚΑ |    1    |     2     |     4     |     8     |    16      ΝΗΜΑΤΑ
       18.977    |  3.641  |   2.164   |   1.469   |   1.867   |    1.701   ΧΡΟΝΟΣ ΣΕ ΔΕΥΤΕΡΟΛΕΠΤΑ

*/

public class WordCountLocal {
    public static void main(String args[]) throws FileNotFoundException, IOException {

        int numThreads=16;

        String fileString = new String(Files.readAllBytes(Paths.get("sixtimesbibe.txt")));//, StandardCharsets.UTF_8);
        String[] words = fileString.split("[ \n\t\r.,;:!?(){}]");

        ConcurrentHashMap<String,Integer> finalMap = new ConcurrentHashMap<>();
        MyThread[] threads = new MyThread[numThreads];

        long startTime = System.nanoTime();

        int blockSize=words.length/numThreads;
        int start=0;
        int stop=0;

        for (int i =0;i<numThreads;i++){
            start=blockSize*i;
            stop = (i == numThreads - 1) ? words.length : start + blockSize;
            threads[i]= new MyThread(words,start,stop);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                //ΠΑΙΡΝΩ ΤΑ ΤΟΠΙΚΑ ΑΠΟΤΕΛΕΣΜΑΤΑ ΚΑΙ ΤΑ ΑΠΟΘΗΕΥΚΩ ΣΤΟ ΤΕΛΙΚΟ MAP
                HashMap<String, Integer> threadResults = threads[i].getLocalWordCount();
                for (Map.Entry<String, Integer> entry : threadResults.entrySet()) {
                    finalMap.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("Χρόνος εκτέλεσης: %.3f δευτερόλεπτα%n", durationInSeconds);

//         for (String name: finalMap.keySet()) {
//         	String key = name.toString();
//         	String value = finalMap.get(name).toString();
//         	System.out.println(key + "\t " + value);
//         }

    }}

class MyThread extends Thread{
    private String[] words;
    private int myStart;
    private int myStop;
    private HashMap<String,Integer> localMap;

    public MyThread(String[] words, int start , int stop){
        this.words=words;
        this.myStart=start;
        this.myStop=stop;
        localMap = new HashMap<>();
    }

    public void run(){
        for (int i=myStart;i<myStop;i++){
            String word = words[i].toLowerCase();
            if (!word.isEmpty()) {
                //ΜΕΘΟΔΟΣ ΤΗΣ MAP ΓΙΑ ΣΥΓΩΝΕΥΣΗ ΤΙΜΩΝ ΧΩΡΙΣ RACE CONDITION
                //ΑΝ Η ΛΕΞΗ ΕΙΝΑΙ ΑΔΕΙΑ ΒΑΖΕΙ 1 ΑΛΛΙΩΣ ΠΡΟΣΘΕΤΕΙ 1 ΣΤΟ ΣΥΝΟΛΟ
                localMap.merge(word, 1, Integer::sum);
            }
        }
    }

    //Επιστρέφω την φομη με τα τοπικα αποτελεσματα
    public HashMap<String,Integer> getLocalWordCount(){
        return localMap;
    }
}





