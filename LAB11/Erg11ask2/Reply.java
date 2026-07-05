import java.io.*;
import java.util.*;

public class Reply implements Serializable {

  private String result;
   
   public Reply(String result) {
	  this.result =result;
   }

   public String getResult(){
    return this.result;
   }
}

