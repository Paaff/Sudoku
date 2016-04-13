package solver;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by minit on 02-03-2016.
 */
public class Tile {
    Field cField;
    private int digit;
    private List<Integer> candidates;


    public Tile(int digit, int max){
        this.digit = digit;
        candidates = new ArrayList<>();

        if(digit == 0){
            for(int i = 1; i <= max ; i++){
                candidates.add(i);
            }
        }
    }

    public void giveField(Field f){
        cField = f;
    }

    public Field getField(){
        return cField;
    }
    public int getDigit(){
        return digit;
    }
    public void setDigit(int i){
        digit = i;
    }
    public List<Integer> getCandidates(){return candidates; }
    public void setCandidates(List<Integer> list){
        candidates = list;
    }
}
