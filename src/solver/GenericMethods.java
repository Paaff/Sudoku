package solver;

import java.util.*;

/**
 * Created by minit on 20-03-2016.
 */
public class GenericMethods {


    public static void findPreemptives(Tile[][] cPuzzle, int x, int y){
        int size = cPuzzle[x][y].getCandidates().size();
        int xAxis = 1;
        int yAxis = 1;
        int fields = 1;

        for(int i = 0 ; i < 9 ; i++){
            if( ( cPuzzle[x][y].getCandidates().equals(cPuzzle[x][i].getCandidates())
                    || cPuzzle[x][y].getCandidates().containsAll( cPuzzle[x][i].getCandidates() ))
                    && !cPuzzle[x][y].equals(cPuzzle[x][i])){
                    yAxis++;

            }

            if( ( cPuzzle[i][y].getCandidates().equals( cPuzzle[i][y].getCandidates() )
                    || cPuzzle[x][y].getCandidates().containsAll( cPuzzle[i][y].getCandidates() ) )
                    && !cPuzzle[x][y].equals(cPuzzle[i][y])){
                    xAxis++;
            }

            if(yAxis == size){

            }

            if(xAxis == size){

            }

        }

        for(Tile t : cPuzzle[x][y].getField().getTiles()){
            if( ( cPuzzle[x][y].getCandidates().equals(t.getCandidates())
                    || cPuzzle[x][y].getCandidates().containsAll(t.getCandidates()) )
                    && !cPuzzle[x][y].equals(t)){

            }
        }

    }




}
