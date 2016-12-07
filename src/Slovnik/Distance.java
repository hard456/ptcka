package Slovnik;

/**
 * Třída, která má proměnnou word a distance. Lze tyto hodnoty nastavovat a vracet.
 */

public class Distance {
    private String word;
    private int distance;

    /**
     * Konstruktor nastaví slovo a distanci.
     * @param word
     * @param distance hodnota získana výpočtem Levenshteinovy metriky.
     */

    public Distance(String word, int distance) {
        this.word = word;
        this.distance = distance;
    }

    /**
     * Vrátí slovo.
     * @return slovo.
     */

    public String getWord() {
        return word;
    }

    /**
     * Vrátí distanci.
     * @return distanci.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Naszaví slovo
     * @param word
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Nastaví distanci
     * @param distance
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Metoda pro vypíš. Vypíše word.
     * @return word
     */
    @Override
    public String toString(){
        return word;
    }

}
