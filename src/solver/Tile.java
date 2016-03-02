package solver;

/**
 * Created by minit on 02-03-2016.
 */
public class Tile {
    Field cField;
    int digit;
    int[] candidates;


    public Tile(int digit, int max){
        this.digit = digit;
        candidates = new int[max];

        if(digit == 0){
            for(int i = 0; i < max ; i++){
                candidates[i] = i + 1;
            }
        }else{
            candidates = null;
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

}
