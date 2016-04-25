package solver;

import java.util.*;

/**
 * Created by minit on 20-03-2016.
 */
public class GenericMethods {

    public static List findEmptyCells(Tile[][] cPuzzle){
        /*
        Deque[] coords = new Deque[2];
        coords[0] = new LinkedList<Integer>();
        coords[1] = new LinkedList<Integer>();
*/
        List<List<Integer>> coords = new ArrayList<>();
        coords.add(new ArrayList<>());
        coords.add(new ArrayList<>());

        for(int i =0 ; i < cPuzzle.length ; i++){
            for(int j = 0 ; j <  cPuzzle.length ; j++){
                if(cPuzzle[i][j].getDigit() == 0){
                    coords.get(0).add(i);
                    coords.get(1).add(j);
                }
            }
        }
        System.out.println(coords.get(0));
        System.out.println(coords.get(1));
        return coords;

    }

    public static void updateXAxis(int y, Tile[][] cPuzzle, List candidates, Tile t){
        for(int i = 0; i < cPuzzle.length ; i++){
            if(cPuzzle[i][y] != t)
                cPuzzle[i][y].getCandidates().removeAll(candidates);
        }

    }

    public static void updateYAxis(int x, Tile[][] cPuzzle, List candidates, Tile t){

        for(int i = 0 ; i < cPuzzle.length ; i++){
            if(cPuzzle[x][i] != t)
                cPuzzle[x][i].getCandidates().removeAll(candidates);
        }
    }

    public static void updateField(Field field, List candidates, Tile s){
        for(Tile t: field.getTiles()){
            if(s!=t)
                t.getCandidates().removeAll(candidates);
        }

    }
    // removes candidate from x-axis when dealing with singles.
    public static void removeCandidateXAxis(int y, Tile[][] cPuzzle, int digit){
        for(int i = 0 ; i<cPuzzle.length ; i++){
            cPuzzle[i][y].getCandidates().remove(new Integer(digit));
        }
    }
    // removes candidates from y-axis when dealing with singles.
    public static void removeCandidateYAxis(int x, Tile[][] cPuzzle, int digit){
        for(int i = 0 ; i<cPuzzle.length ;i++){
            cPuzzle[x][i].getCandidates().remove(new Integer((digit)));
        }
    }
    //removes candidates from field when dealing with singles.
    public static void removeCandidateField(Field field, int digit){
        for(Tile t:field.getTiles()){
            t.getCandidates().remove(new Integer(digit));
        }
    }
    //removes candidates from x-axis when dealing with pointing pairs, and acts as a change-checker.
    public static boolean removeLockedCandidatesXAxis(int y, Tile[][] cPuzzle, Field f, int digit){
        boolean result = false;

        for(int i = 0; i<cPuzzle.length;i++){
            if(cPuzzle[i][y].getField()!=f && cPuzzle[i][y].getCandidates().contains(digit)){
                cPuzzle[i][y].getCandidates().remove(new Integer(digit));
                result = true;
            }
        }
        return result;
    }
    //removes candidates from y-axis when dealing with pointing pairs, and acts as a change-checker.
    public static boolean removeLockedCandidatesYAxis(int x, Tile[][] cPuzzle, Field f, int digit){
        boolean result = false;

        for(int i = 0; i<cPuzzle.length;i++){
            if(cPuzzle[x][i].getField()!=f && cPuzzle[x][i].getCandidates().contains(digit)){
                cPuzzle[x][i].getCandidates().remove(new Integer(digit));
                result = true;
            }
        }
        return result;
    }
    //removes candidates from field when dealing with claiming pairs, and acts as change-checker.
    public static boolean removeClaimedCandidates(List<Tile> tiles,Field f, int digit){
        boolean result = false;
        for(Tile t:f.getTiles()){
            if(!tiles.contains(t) && t.getCandidates().contains(digit)){
                t.getCandidates().remove(new Integer(digit));
                result = true;
            }
        }
        return  result;
    }

    public static boolean removeNakedPairField(Tile[][] cPuzzle, Tile[] pair){
        boolean result = false;

        for(Tile t : pair[0].getField().getTiles()){
            if(pair[0] != t && pair[1] != t && t.getCandidates().removeAll(pair[0].getCandidates())){
                result = true;
            }
        }

        return result;
    }

    public static boolean removeNakedPairRow(Tile[][] cPuzzle, Tile[] pair){
        boolean result = false;

        int y = pair[0].getY();

        for(int x = 0; x < cPuzzle.length ; x++){
            if(pair[0] != cPuzzle[x][y] && pair[1] != cPuzzle[x][y] &&
                    cPuzzle[x][y].getCandidates().removeAll(pair[0].getCandidates())){
                result = true;
            }
        }

        return result;
    }

    public static boolean removeNakedPairColumn(Tile[][] cPuzzle, Tile[] pair){
        boolean result = false;

        int x = pair[0].getX();

        for(int y = 0; y <cPuzzle.length ; y++){
            if(pair[0] != cPuzzle[x][y] && pair[1] != cPuzzle[x][y]
                    && cPuzzle[x][y].getCandidates().removeAll(pair[0].getCandidates())){
                result = true;
            }
        }

        return result;
    }

    public static boolean containsSome(List<Integer> list1, List<Integer> list2){
        for(Integer i : list2){
            if(list1.contains(i))
                return true;
        }
        return false;
    }

    public static boolean listSizeCheck(List<Integer> list1, List<Tile> list2, int size){
        Set<Integer> temp = new HashSet<>();
        temp.addAll(list1);

        if(size == 4 && list2.size() == 4 && temp.size() == 4){
             int index = 0;
        }

        if(temp.size()== size && list2.size() == size)
            return true;

        return false;
    }

    public static boolean removeNakedSubsetRow(Tile[][] cPuzzle, List<Tile> tiles, List<Integer> list){
        boolean result = false;
        int y = tiles.get(0).getY();

        Set<Integer> temp = new HashSet<>();
        temp.addAll(list);

        for(int x = 0 ; x <cPuzzle.length ; x++){
            if(!tiles.contains(cPuzzle[x][y]) && cPuzzle[x][y].getCandidates().removeAll(temp)){
                result = true;

            }
        }
        return result;
    }

    public static boolean removeNakedSubsetColumn(Tile[][] cPuzzle, List<Tile> tiles, List<Integer> list){
        boolean result = false;
        int x = tiles.get(0).getX();

        Set<Integer> temp = new HashSet<>();
        temp.addAll(list);

        for(int y = 0 ; y <cPuzzle.length ; y++){
            if(!tiles.contains(cPuzzle[x][y]) && cPuzzle[x][y].getCandidates().removeAll(temp)){
                result = true;

            }
        }
        return result;
    }
    public static boolean removeNakedSubsetField(Tile[][] cPuzzle, List<Tile> tiles, List<Integer> list){
        boolean result = false;

        Set<Integer> temp = new HashSet<>();
        temp.addAll(list);

        for(Tile t: tiles.get(0).getField().getTiles()){
            if(!tiles.contains(t) && t.getCandidates().removeAll(temp)){
                result = true;

            }
        }
        return result;
    }

    public static boolean removeXWingColumn(Tile[][] cPuzzle, List<Tile> columnTiles, int digit){
        boolean result = false;
        int y1 = columnTiles.get(0).getY();
        int y2 = columnTiles.get(1).getY();
        for(int x = 0 ; x < cPuzzle.length ; x++){
            if(!columnTiles.contains(cPuzzle[x][y1])){
                if(cPuzzle[x][y1].getCandidates().remove(new Integer(digit))){
                    result = true;
                }
            }

            if(!columnTiles.contains(cPuzzle[x][y2])){
                if(cPuzzle[x][y2].getCandidates().remove(new Integer(digit))){
                    result = true;
                }
            }

        }

        return result;
    }

    public static boolean removeXWingRows(Tile[][] cPuzzle, List<Tile> rowTiles, int digit){
        boolean result = false;
        int x1 = rowTiles.get(0).getX();
        int x2 = rowTiles.get(1).getX();
        for(int y = 0 ; y <cPuzzle.length ; y++){
            if(!rowTiles.contains(cPuzzle[x1][y])){
                result = cPuzzle[x1][y].getCandidates().remove(new Integer(digit));

            }

            if(!rowTiles.contains(cPuzzle[x2][y])){

                result = cPuzzle[x2][y].getCandidates().remove(new Integer(digit));

            }
        }
        return result;

    }

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
