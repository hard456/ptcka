package Slovnik;

/**
 * Created by HARD on 05.12.2016.
 */
public class Distance {
    private String word;
    private int distance;

    public Distance(String word, int distance) {
        this.word = word;
        this.distance = distance;
    }

    public String getWord() {
        return word;
    }

    public int getDistance() {
        return distance;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString(){
        return word;
    }

}
