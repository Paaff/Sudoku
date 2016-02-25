package reader;

import java.io.File;
import java.util.Scanner;


/**
 * Created by minit on 25-02-2016.
 */

public class PuzzleReader {

    int[][] sPuzzle; // Where the loaded puzzle is stored.
    File file;
    int pSize;
    String s = "src\\reader\\puzzle3_1.txt";
    public void runReader(){

        file = new File(s);

        try{
            Scanner sc = new Scanner(file);
            int tmp =0;
            pSize = Integer.parseInt(sc.nextLine());

            sPuzzle = new int[pSize*pSize][pSize*pSize];

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


    private void pPuzzle(int[][] tPuzzle){

        System.out.println(pSize);

        for(int i = 0; i < pSize*pSize ; i++ ){
            if(i == 3 || i == 6) {
                for(int y = 0 ; y < 9 ; y++) {
                    System.out.print("---");
                }
                System.out.println();
            }

            for(int j = 0; j < pSize*pSize ; j++){
                if(j == 2 || j == 5){
                    System.out.print(tPuzzle[j][i] + " | ");
                }else {
                    System.out.print(tPuzzle[j][i] + "  ");
                }


            }
            System.out.println();
        }
    }
}
