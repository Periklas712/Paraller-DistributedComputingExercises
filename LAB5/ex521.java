import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ex521 {
	//ΘΑ ΒΑΛΩ ΤΗΝ ΕΙΚΟΝΑ ΝΑ ΕΙΝΑΙ GLOBAL ΜΕΤΑΒΛΗΤΗ ΩΣΤΕ ΝΑ ΜΗΝ ΤΗΝ ΠΕΡΝΑΩ ΩΣ ΟΡΙΣΜΑ ΣΕ ΚΑΘΕ ΝΗΜΑ
	//ΔΟΚΙΜΑΣΑ ΚΑΙ ΝΑ ΤΗΝ ΠΕΡΝΑΩ ΣΕ ΚΑΘΕ ΝΗΜΑ ΚΙΑ ΕΧΩ ΚΑΛΥΤΕΡΗ ΒΕΛΤΙΩΣΗ ΩΣ GLOBAL
	static BufferedImage img;


   public static void main(String args[]) {

	   //ΤΟ ΛΑΠΤΟΠ ΜΟΥ ΕΧΕΙ 16 ΠΥΡΝΗΕΣ ΟΠΟΤΕ ΘΑ ΚΑΝΩ ΔΟΚΙΜΕΣ ΚΑΙ ΜΕ ΑΥΤΟ
	   //ΒΛΕΠΩ ΟΙ ΧΡΟΝΟΙ ΟΣΟ ΑΥΞΑΝΟΝΤΑΙ ΤΑ ΝΗΜΑΤΑ ΔΕΝ ΒΕΛΤΙΩΝΟΝΤΑΙ,ΔΟΚΙΜΑΣΑ ΝΑ ΠΕΡΝΑΩ ΤΗΝ ΕΙΚΟΝΑ ΣΕ ΚΑΘΕ ΝΗΜΑ ΑΛΛΑ ΔΕΝ ΕΙΧΕ ΜΕΓΑΛΗ ΔΙΑΦΟΡΑ
	   //ΔΕΝ ΜΠΟΡΩ ΝΑ ΚΑΤΑΛΑΒΩ ΑΚΡΙΒΩΣ ΓΙΑΤΙ ΔΕΝ ΕΙΝΑΙ ΚΑΛΥΤΕΡΟΙ ΟΙ ΧΡΟΝΟΙ...
	   //ΙΣΩΣ ΤΑ BLOCKS ΗΤΑΝ TILES  ΕΙΚΟΝΑ ΚΑΙ ΟΧΙ ΓΡΑΜΜΕΣ

	   /*
	   ΦΩΤΟΓΡΑΦΙΑ    |  1  |  2  |  4  |  8  |  16   |	THREADS
	   1house.jpg	 |	210	  179	220	  256	 255 |	ΧΡΟΝΟΣ ΣΕ ms
	   2aerial.jpg	 |  361   320   406   357    406 |	ΧΡΟΝΟΣ ΣΕ ms
	   3tiger.jpg    |	923	  902	882   966	 864 |  ΧΡΟΝΟΣ ΣΕ ms
	   4food.jpg     |  902   681   786   906    804 | 	ΧΡΟΝΟΣ ΣΕ ms
	   5landscape.jpg|  666   537   670   871    764 |  ΧΡΟΝΟΣ ΣΕ ms
	   6berries.jpg  |  757   726   676   868    910 |  ΧΡΟΝΟΣ ΣΕ ms
	   7lake.jpg     | 1078   778   969  1066   1009 |  ΧΡΟΝΟΣ ΣΕ ms
	    */

	   String fileNameR = null;
	   String fileNameW = null;
	   int numThreads = 2;
		// //Input and Output files using command line arguments
		// if (args.length != 2) {
		// 	System.out.println("Usage: java RGBtoGrayScale <file to read > <file to write>");
		// 	System.exit(1);
		// }
//		fileNameR = args[0];
//		fileNameW = args[1];


	   MyThread threads[] = new MyThread[numThreads];

		//The same without command line arguments
		
		fileNameR = null;
		fileNameW = "new.jpg";
				
		//Reading Input file to an image
		//BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(fileNameR));
		} catch (IOException e) {}

        //Start timing
		long start = System.currentTimeMillis();

		//Θα κοψω την εικονα σε block γραμμων
	   //αρα θα παρω το υψοσ και θα το διαιρεσω με τον αριθμο τον πυρηνων
	   //επειτα καθε thread  παιρνει ενα block γραμμων
	   int block = img.getHeight()/numThreads;

	   for (int i = 0; i < numThreads; i++) {
		  int from = i*block;
		  int end=i*block+block;
		   if ((numThreads-1)==i) end=img.getHeight();
		   threads[i] = new MyThread(from, end);
		   threads[i].start();
	   }

	   for(int i = 0; i < numThreads; i++) {
		   try {
			   threads[i].join();
		   } catch (InterruptedException e) {}
	   }
	
	    //Stop timing
	    long elapsedTimeMillis = System.currentTimeMillis()-start;
       
		//Saving the modified image to Output file
		try {
		  File file = new File(fileNameW);
		  ImageIO.write(img, "jpg", file);
		} catch (IOException e) {}
      
		System.out.println("Done...");
		System.out.println("time in ms = "+ elapsedTimeMillis);
   }
}

class MyThread extends Thread {
	private static final double redCoefficient = 0.299;
	private static final double greenCoefficient = 0.587;
	private static final double blueCoefficient = 0.114;
	int myStart;
	int myEnd;

	public MyThread(int myStart, int myEnd) {

		this.myStart = myStart;
		this.myEnd = myEnd;
	}

	public void run() {
		for (int y = myStart; y < myEnd; y++) {
			for (int x = 0; x < ex521.img.getWidth(); x++) {
				//Retrieving contents of a pixel
				int pixel = ex521.img.getRGB(x,y);
				//Creating a Color object from pixel value
				Color color = new Color(pixel, true);
				//Retrieving the R G B values, 8 bits per r,g,b
				//Calculating GrayScale
				int red = (int) (color.getRed() * redCoefficient);
				int green = (int) (color.getGreen() * greenCoefficient);
				int blue = (int) (color.getBlue() * blueCoefficient);
				//Creating new Color object
				color = new Color(red+green+blue,
						red+green+blue,
						red+green+blue);
				//Setting new Color object to the image
				ex521.img.setRGB(x, y, color.getRGB());
			}
		}

	}

}
