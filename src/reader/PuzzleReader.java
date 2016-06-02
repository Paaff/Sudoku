package reader;

import solver.Field;
import solver.Tile;

import java.io.File;
import java.util.Scanner;


/**
 * Created by minit on 25-02-2016.
 */

public class PuzzleReader {

    public Tile[][] sPuzzle; // Where the loaded puzzle is stored.
    Field[][] sFields;
    File file;
    public int pSize; // The size of the puzzle. It is the first number read from the file.
   // String s = "src\\reader\\puzzle3_20.txt"; // Source of the file

    public Tile[][] runReader(String s){

        file = new File("src\\reader\\" + s);

        try{
            Scanner sc = new Scanner(file);
            int tmp =0;
            pSize = Integer.parseInt(sc.nextLine());

            /*
                After it has read the first line, it is able to know the size of the puzzle and
                can match the array's size.
             */
            sPuzzle = new Tile[pSize*pSize][pSize*pSize];

            /*
                Reading the rest of the file, inserting the numbers in all of the right places of the array
                and a simple 0 where it reads "."
             */
            while(sc.hasNextLine()) {
                String tString = sc.nextLine();
                String[] tList = tString.split(";");

                for(int i = 0 ; i < sPuzzle.length ; i++){

                    try{
                        int j = Integer.parseInt(tList[i]);
                        sPuzzle[i][tmp] = new Tile(j, sPuzzle.length,i,tmp);
                    }catch(NumberFormatException e){
                        sPuzzle[i][tmp] = new Tile(0, sPuzzle.length,i,tmp);
                    }
                }
                tmp++;
            }


        } catch (Exception ex){
            ex.printStackTrace();
        }

       // pPuzzle(sPuzzle);
        return sPuzzle;
    }

    // Creating the individual fields.
    public Field[][] setUpFields(){
        sFields = new Field[pSize][pSize];

        for(int i = 0 ; i < sFields.length ; i++){
            for(int j = 0 ; j < sFields.length ; j++) {
                sFields[i][j] = new Field(pSize * pSize);
            }
        }

        for(int i = 0 ; i < pSize*pSize ; i++){
            for(int j = 0 ; j < pSize*pSize ; j++){
                sFields[i/pSize][j/pSize].giveTile(sPuzzle[i][j]);
            }
        }

        return sFields;
    }

    /*
        Method for printing the puzzle in the console.
     */
    public void pPuzzle(Tile[][] cPuzzle){

        System.out.println("The size of the puzzle: " + pSize);

        for(int i = 0; i < pSize*pSize ; i++ ){
            if((i) % pSize == 0 && i != 0) {
                for(int y = 0 ; y < pSize*pSize ; y++) {
                    System.out.print("---"); // Line breaks for showing the n*n fields
                }
                System.out.println();
            }

            for(int j = 0; j < pSize*pSize ; j++){
                if((1 + j) % pSize == 0 && j != pSize*pSize-1){
                    System.out.print(cPuzzle[j][i].getDigit() + " | "); // Line breaks for showing the n*n fields
                }else {
                    System.out.print(cPuzzle[j][i].getDigit() + "  ");
                }


            }
            System.out.println();
        }
    }
}
