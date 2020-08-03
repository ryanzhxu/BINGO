import java.util.ArrayList;

public class Interval {

    private ArrayList<Integer> numInterval;
    private int start, end;

//    void fillInterval (int start, int end) {
//        if (start !=)
//        this.numInterval = new ArrayList<Integer>(end - start);
//        int i=start;
//        while (i<end)
//            numInterval.set(i, i++);
//    }

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getStart() {return start;}

    public int getEnd() {return end;}

    public void setStart(int start) {this.start = start;}

    public void setEnd(int end) {this.end = end;}

    public boolean withinBoundary (int guessNum) {
        return guessNum >= start && guessNum <= end;
    }

    public void updateInterval (int guessNum, int randomNum) {
        if (guessNum <= randomNum)   setStart (guessNum);
        else setEnd (guessNum);
    }

    public void printInterval() {
        System.out.println("The Range is now: " + start + "  -  " + end);
    }








}
