import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.*;

// ΠΑΡΑΛΛΗΛΟΣ ΧΡΟΝΟΣ ΕΚΤΕΛΕΣΗΣ ΓΙΑ ΤΟ ΑΡΧΙΚΟ ΑΡΧΕΙΟ (bible.txt = 3.85 MB = 4,047,392 bytes) ---> 0.294 δευτερόλεπτα
// ΠΑΡΑΛΛΗΛΟΣ ΧΡΟΝΟΣ ΕΚΤΕΛΕΣΗΣ ΓΙΑ ΤΟ ΜΕΓΑΛΟ ΑΡΧΕΙΟ (311 MB = 326,222,160 bytes)            ---> 2.848 δευτερόλεπτα

// ΕΦΟΣΟΝ ΕΧΩ ΔΙΑΘΕΣΙΜΟΥΣ 16 ΠΥΡΗΝΕΣ ΠΑΡΑΤΗΡΩ ΟΤΙ ΟΙ ΧΡΟΝΟΙ ΣΕ ΑΥΤΗΝ ΤΗΝ ΛΥΣΗ ΕΙΝΑΙ ΚΑΛΥΤΕΡΟΙ ΑΠΟ ΤΙΣ ΑΛΛΕΣ
// ΣΤΗΝ ΚΑΛΥΤΕΡΗ ΕΠΙΔΟΣΗ ΣΤΙΣ ΑΛΛΕΣ ΛΥΣΕΙΣ ΕΧΩ ΧΡΟΝΟ ~2 sec ΜΕ 4 ΠΥΡΗΝΕΣ ΕΝΩ ΕΔΩ ΜΕ 16 ΠΥΡΗΝΕΣ ~3 ΔΕΥΤ sec

public class WordCountParallelStreams {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("sixtimesbibe.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(fileInputStream));
        long startTime = System.nanoTime();


        ConcurrentMap<String,Long> wordCount= in.lines().parallel()
        .flatMap(line -> Arrays.stream(line.split("[ \n\t\r.,;:!?(){}\"]+"))) //ΒΑΖΩ ΤΙΣ ΛΕΞΕΙΣ ΣΤΟ STREAM 
        .filter(word -> word.length() > 0)
        .map(String::toLowerCase)
        .collect(Collectors.groupingByConcurrent( //THREAD SAFE
            word -> word, //ΤΟ ΚΛΕΙΔΙ ΕΙΝΑΙ ΤΟ WORD EDΟ 
            Collectors.counting() //Η ΤΙΜΗ ΕΙΝΑΙ ΑΥΤΟ ΠΟΥ ΑΥΞΑΝΕΤΑΙ Ο ΑΡΙΘΜΟΣ ΜΕΦΑΝΙΣΕΩΝ 
        ));

      
        in.close();
        fileInputStream.close();
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        System.out.printf("Χρόνος εκτέλεσης: %.3f δευτερόλεπτα%n", durationInSeconds);

        // for(Object key : wordCount.keySet()) {
        //     System.out.println(key + ":" + wordCount.get(key));
        // }
    }
}
