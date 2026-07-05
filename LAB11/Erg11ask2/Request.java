import java.io.*;
import java.util.*;

public class Request implements Serializable {

   

   private String number;
	private Long num;
   
   public Request() {
      number="";
   }

   public void setNumber() throws IOException{
      BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
     while (true) {
			System.out.print("Please enter a number of steps to calculate pi (or -1 to exit): ");
			number = user.readLine();

		if (number.equals("-1")) {
			num=(long) -1;
		}
			try {
				num = Long.parseLong(number);
				if (num > 0) {
					break;
				} else {
					System.out.println("Number of steps must be a positive integer.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid positive integer.");
			}
		}}
   
   public Long getNumber(){
      return this.num;
   }
   
   
   }

