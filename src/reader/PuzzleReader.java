package reader;

import java.io.File;
import java.util.Scanner;


/**
 * Created by minit on 25-02-2016.
 */

public class PuzzleReader {

    public int[][] sPuzzle; // Where the loaded puzzle is stored.
    File file;
    public int pSize; // The size of the puzzle. It is the first number read from the file.
    String s = "src\\reader\\puzzle3_1.txt"; // Source of the file

    public void runReader(){

        file = new File(s);

        try{
            Scanner sc = new Scanner(file);
            int tmp =0;
            pSize = Integer.parseInt(sc.nextLine());

            /*
                After it has read the first line, it is able to know the size of the puzzle and
                can match the array's size.
             */
            sPuzzle = new int[pSize*pSize][pSize*pSize];

            /*
                Reading the rest of the file, inserting the numbers in all of the right places of the array
                and a simple 0 where it reads "."
             */
            while(sc.hasNextLine()) {
                String tString = sc.nextLine();
                String[] tList = tString.split(";");

                for(int i = 0 ; i < 9 ; i++){

                    try{
                        sPuzzle[i][tmp] = Integer.parseInt(tList[i]);
                    }catch(NumberFormatException e){
                        sPuzzle[i][tmp] = 0;
                    }
                }
                tmp++;
            }


        } catch (Exception ex){
            ex.printStackTrace();
        }

        pPuzzle(sPuzzle);

    }

    /*
        Method for printing the puzzle in the console.
     */


    private void pPuzzle(int[][] tPuzzle){


        System.out.println(pSize);


        for(int i = 0; i < pSize*pSize ; i++ ){
            if(i == 3 || i == 6) {
                for(int y = 0 ; y < 9 ; y++) {
                    System.out.print("---"); // Line breaks for showing the n*n fields
                }
                System.out.println();
            }

            for(int j = 0; j < pSize*pSize ; j++){
                if(j == 2 || j == 5){
                    System.out.print(tPuzzle[j][i] + " | "); // Line breaks for showing the n*n fields
                }else {
                    System.out.print(tPuzzle[j][i] + "  ");
                }


            }
            System.out.println();
        }
    }
}
