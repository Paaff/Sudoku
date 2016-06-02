package solver2;

import solver.CandidateFinder;
import solver.GenericMethods;
import solver.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minit on 21-03-2016.
 */
public class Algorithm1 {

    public static void runAlgorihm(Tile[][] cPuzzle) {

        CandidateFinder.runFinder(cPuzzle);
        List<List<Integer>> emptyCells = GenericMethods.findEmptyCells(cPuzzle);
        List<List<Integer>> tempEmpty = new ArrayList<>();
        tempEmpty.add(new ArrayList<>());
        tempEmpty.add(new ArrayList<>());

        int oldSize = 0;

        while(oldSize != emptyCells.get(0).size() && emptyCells.get(0).size() != 0) {
            System.out.println("Size of emptyCells: " + emptyCells.get(0).size() );


            // Base case if a Tile only has one candidate.
            for (int i = 0; i < emptyCells.get(0).size(); i++) {
                int xPos = emptyCells.get(0).get(i);
                int yPos = emptyCells.get(1).get(i);
                if (cPuzzle[xPos][yPos].getCandidates().size() == 1) {
                    cPuzzle[xPos][yPos].setDigit(cPuzzle[xPos][yPos].getCandidates().get(0));

                    GenericMethods.updateField(cPuzzle[xPos][yPos].getField(),
                            cPuzzle[xPos][yPos].getCandidates(), cPuzzle[xPos][yPos]);

                    GenericMethods.updateXAxis(yPos, cPuzzle,
                            cPuzzle[xPos][yPos].getCandidates(), cPuzzle[xPos][yPos]);

                    GenericMethods.updateYAxis(xPos, cPuzzle,
                            cPuzzle[xPos][yPos].getCandidates(), cPuzzle[xPos][yPos]);

                    cPuzzle[xPos][yPos].getCandidates().clear();


                } else {

                    tempEmpty.get(0).add(xPos);
                    tempEmpty.get(1).add(yPos);
                }
            }

            oldSize = emptyCells.get(0).size();

            emptyCells.get(0).clear();
            emptyCells.get(1).clear();

            emptyCells.get(0).addAll(tempEmpty.get(0));
            emptyCells.get(1).addAll(tempEmpty.get(1));

            tempEmpty.get(0).clear();
            tempEmpty.get(1).clear();

        }
    }
}
