// Samantha Sudhoff
// AP Comp Sci Pd. 1
// December 21, 2020
// Project 7: Life

import java.util.*;
import java.io.*;
import java.awt.*;
public class Life {

        public static final int PIXEL_SIZE = 5; 
        
        /**
        * Runs main method, gets input and ouput files, gets number of frames and time between steps, prints to output file
        *
        */
        public static void main(String[] args) throws FileNotFoundException { 
                System.out.println("This program runs Conway's Game of Life");
                Scanner input = new Scanner(System.in);
                Scanner fileScanner = MyUtils.getFileScanner();
                PrintStream output = MyUtils.fileWriter(input);
                int numReps = MyUtils.getNumber(input, "Number of frames to run the simulation (0-5000): ", 0, 5000);
                int tBtwn = MyUtils.getNumber(input, "Time between steps in ms (1-5000): ", 1, 5000);
                int[][] cGen = readInputFile(fileScanner);
                if (cGen == null) { 
                        return;
                } 
                int[][] nextGen = drawSimulation(cGen, numReps, tBtwn);
                printOutput(nextGen, output);
                
                System.out.println("Simulation successful!");
        } 
        
        /**
        * reads input file and converts contents to array of 1's and 0's 
        * @param fileScanner that scans the input file contents
        * @return int[][] array called cGen, which is the current generation that is to be modified
        */
        public static int[][] readInputFile(Scanner fileScanner) { 
                String line = "";
                if (fileScanner.hasNextLine()) { 
                        line = fileScanner.nextLine();
                } 
                else {
                        System.out.println("Error found in the input file. Halting simulation.");
                } 
                Scanner lineScanner = new Scanner(line);
                int dim1 = lineScanner.nextInt();
                int dim2 = lineScanner.nextInt();
                int[][] cGen = new int[dim1][dim2];
                int r = 0;
                int c = 0;
                int count = 0;
                while (fileScanner.hasNextLine()) { 
                        count++;
                        c = 0; 
                        line = fileScanner.nextLine();
                        lineScanner = new Scanner(line);
                        String cellToken = lineScanner.next();   
                        if (cellToken.length() != dim2) { 
                                System.out.println("Error found in the input file. Halting simulation.");
                                return null;
                        }                      
                        for (int i = 0; i < cellToken.length() - 1; i++) { 
                                String cell = cellToken.substring(i, i+1);
                               
                                if (cell.equals(".")) { 
                                        cGen[r][c] = 0;    
                                } 
                                else {
                                        cGen[r][c] = 1;
                                }
                                        
                                        c++;                
                        }
                        
                        String cell = cellToken.substring(cellToken.length() - 1, cellToken.length());
                        if (cell.equals(".")) {
                                cGen[r][c] = 0;
                        }
                        else {
                                cGen[r][c] = 1;
                        } 
                               
                        r++; 
                              
               } 
 
               return cGen;      
        } 
        
        /**
        * goes through 1 iteration of modifying the current generation based on the neighbors of cells
        * @param cGen which is current generation 
        * @return int[][] nextGen which is the next generation
        */
        public static int[][] iteration(int[][] cGen) { 
                int[][] nextGen = new int[cGen.length][cGen[0].length]; 
                for (int r = 0; r < cGen.length; r++) { 
                        for (int c = 0; c < cGen[0].length; c++) { 
                                int count = 0;
                                if (cGen[r][c] == 1 || cGen[r][c] == 0) { 
                                        if (r != 0 && cGen[r - 1][c] == 1) { 
                                                count++;
                                        } 
                                        if (r != 0 && c != 0 && cGen[r - 1][c - 1] == 1) {
                                                count++;
                                        }
                                        if (r != 0 && c != cGen[0].length - 1 && cGen[r - 1][c + 1] == 1) { 
                                                count++;
                                        } 
                                        if (c != 0 && cGen[r][c - 1] == 1) { 
                                                count++;
                                        } 
                                        if (c != cGen[0].length - 1 && cGen[r][c + 1] == 1) {
                                                count++;
                                        } 
                                        if (r != cGen.length - 1 && cGen[r + 1][c] == 1) { 
                                                count++;
                                        }
                                        if (r != cGen.length - 1 && c != 0 && cGen[r + 1][c - 1] == 1) { 
                                                count++;
                                        } 
                                        if (r != cGen.length - 1 && c != cGen[0].length - 1 && cGen[r + 1][c + 1] == 1) { 
                                                count++;
                                        } 
                                                
                                        nextGen[r][c] = checkCount(count, r, c, nextGen, cGen);
                                }    
                        } 
                } 
                return nextGen;
        } 
        
        /**
        * Checks if a cell should live or die based on the number of live neighbors
        * @param count which is the number of live neighbors
        * @param r which is the row number of nextGen
        * @param c which is the column number of nextGen
        * @param nextGen which is the next generation
        * @param cGen which is the current generation 
        * @return nextGen[r][c] which is the value of a cell in nextGen (0 = dead, 1 = alive)
        */
        public static int checkCount(int count, int r, int c, int[][] nextGen, int[][] cGen) { 
                if (count < 2) {
                        nextGen[r][c] = 0;
                } 
                else if (2 <= count && count <= 3 && cGen[r][c] == 1) { 
                        nextGen[r][c] = 1;
                } 
                else if (count == 3 && cGen[r][c] == 0) { 
                        nextGen[r][c] = 1;
                } 
                return nextGen[r][c];
        } 
        
        /**
        * prints the contents of the final generation to the output file 
        * @param nextGen which is the generation that is going to be transferred to the output file
        * @param output which is the printstream for the output file 
        */
        public static void printOutput(int[][] nextGen, PrintStream output) { 
                for(int r = 0; r < nextGen.length; r++) { 
                        for(int c = 0; c < nextGen[0].length; c++) { 
                                if(nextGen[r][c] == 1) { 
                                        output.print("x");
                                } 
                                else { 
                                        output.print(".");
                                } 
                        } 
                        output.println();
                } 
        } 
         
         /**
         * draws the simulation on a drawing panel
         * @param cGen which is the current generation
         * @param numReps which is the number of iterations that the simulation should be run
         * @param tBtwn which is the number of ms between frames
         * @return nextGen, which is the next generation
         */
         public static int[][] drawSimulation(int[][] cGen, int numReps, int tBtwn) { 
                DrawingPanel panel = new DrawingPanel(cGen[0].length * PIXEL_SIZE, cGen.length * PIXEL_SIZE);
                Graphics g = panel.getGraphics();
                int [][] nextGen = iteration(cGen);
                int x = 0; 
                int y = 0;
                for (int r = 0; r < cGen.length; r++) { 
                        y = 0;
                        for (int c = 0; c < nextGen[0].length; c++) { 
                                if (cGen[r][c] == 1) { 
                                        g.setColor(Color.BLACK); 
                                        g.fillRect(y, x, PIXEL_SIZE, PIXEL_SIZE);
                                } 
                                y = y + PIXEL_SIZE;
                                
                        } 
                        x = x + PIXEL_SIZE;
                } 
              
                for (int i = 1; i < numReps; i++) { 
                        g.setColor(Color.WHITE);
                        g.fillRect(0,0, cGen[0].length * PIXEL_SIZE, cGen.length * PIXEL_SIZE);
                        x = 0; 
                        y = 0;
                        cGen = nextGen;
                        for (int r = 0; r < cGen.length; r++) { 
                                y = 0;
                                for (int c = 0; c < cGen[0].length; c++) { 
                                        if(cGen[r][c] == 1) { 
                                                g.setColor(Color.BLACK);
                                                g.fillRect(y, x, PIXEL_SIZE, PIXEL_SIZE);    
                                        } 
                                        y = y + PIXEL_SIZE;
                                           
                                } 
                                x = x + PIXEL_SIZE;
                        } 
                        if (i < numReps - 1) { 
                                nextGen = iteration(cGen);
                        } 
                        panel.sleep(tBtwn);
                        
                }    
                return nextGen;
        }  
}
