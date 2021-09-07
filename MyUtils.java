// Samantha Sudhoff
// AP Comp Sci Pd. 1
// MyUtils for multiple projects
// November 18, 2020

import java.util.Scanner;
import java.io.*;
public class MyUtils {
   
   /**
   * gets user input for integer between two values and checks to see if user input is valid
   * @param input user input
   * @param prompt is the prompted information from the user
   * @param min is minimum value
   * @param max is maximum value
   * @return num is number from user within specified range
   */
   public static int getNumber(Scanner input, String prompt, int min, int max) {
      while (true) {
         System.out.print(prompt);
         if(!input.hasNextInt()) {
            input.next();
            System.out.println("You must enter an *integer* between " + min + " and " + max + ".");
         } 
         else {
            int num = input.nextInt();
            if(num < min || num > max) {
               System.out.println("Your number needs to be between " + min + " and " + max + ".");
            }
            else {
               return num;
            }
         }
      }
   }
   
   /**
    * Gets a string input and checks to see if it is blank
    * @param input which is user input
    * @param prompt which is prompt for user response
    * @return String which is the string from user
    */
   public static String getString(Scanner input, String prompt) {
      while (true) {
         System.out.print(prompt);
         String text = input.nextLine();
         if(text.isBlank()) {
            System.out.println("Input is not valid, you need to enter some text");
            //input.nextLine();
         }
         else {
            return text;
         }
      }
   }

   /**
   * gets input file
   * @return fileScanner which scans input file
   */       
   public static Scanner getFileScanner() throws FileNotFoundException{ 
      String filename = "";
      Scanner console = new Scanner(System.in);
      System.out.print("Input file name? ");
      filename = console.nextLine();
      while (!(new File(filename).exists())) {
         System.out.print("File not found. Try again: ");
         filename = console.nextLine();
      }
      Scanner fileScanner = new Scanner(new File(filename)); 
      return fileScanner; 
   } 

   /**
   * creates output file and printstream for output file
   * @param input is scanner input
   * @return output is the PrintStream of the output
   */
   public static PrintStream fileWriter(Scanner input) throws FileNotFoundException {
      
      System.out.print("Output file name? ");
      String filename = input.next().toLowerCase();
      File outputFile = new File(filename);
      PrintStream output = new PrintStream(outputFile);
      return output;
      
   }
   
   /**
    * Makes an output file based on a string name and prints the output to the output file
    * @param level which is current level
    * @param BASE_NAME which is the base name of the file to be read in
    * @param EXTENSION which is the file extension
    * @return PrintStream which is a print stream
    * @throws FileNotFoundException
    */
   public static PrintStream dragonFileWriter(int level, String BASE_NAME, String EXTENSION) throws FileNotFoundException { 
        String filename = BASE_NAME + level + EXTENSION;
        File outputFile = new File(filename);
        PrintStream output = new PrintStream(outputFile);
        return output;
   } 

}