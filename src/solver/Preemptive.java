package solver;

/**
 * Created by minit on 21-03-2016.
 */
public class Preemptive {

    int[] numbers;
    int[][] cells;
    PreemtiveState state;
    public Preemptive(int[] numbers, int[][] cells, PreemtiveState s){
        state = s;
        this.numbers = numbers;
        this.cells=cells;
    }
}
